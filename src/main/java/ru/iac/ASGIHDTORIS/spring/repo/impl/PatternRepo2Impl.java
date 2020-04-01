package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.PatternMapper;
import ru.iac.ASGIHDTORIS.spring.component.SourceCountMapper;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatternRepo2Impl implements PatternRepo2 {

    private final String NAME = "pattern";

    private final PatternMapper patternMapper;
    private final SourceCountMapper sourceCountMapper;
    private final FullRepoHelper<Pattern> fullRepoHelper;

    public PatternRepo2Impl(PatternMapper patternMapper, SourceCountMapper sourceCountMapper, FullRepoHelper<Pattern> fullRepoHelper) {
        this.patternMapper = patternMapper;
        this.sourceCountMapper = sourceCountMapper;
        this.fullRepoHelper = fullRepoHelper;
    }

    @Override
    public Page<Pattern> findAllSourceByQuery(Pageable pageable, PatternModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(pattern, params);
        String pageQuery = fullRepoHelper.createPageQuery(pageable, pattern.getHelpModel().getSort(), pattern.getHelpModel().getKey());

        List<Pattern> patterns = fullRepoHelper.getAll(NAME, valueQuery, pageQuery, params, patternMapper);
        int count = fullRepoHelper.getCount(NAME, valueQuery, params, sourceCountMapper);

        return new PageImpl<>(patterns, pageable, count);
    }

    private String createQueryValue(PatternModel pattern, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null  && !pattern.getId().equals("")) {
            values.add(" cast(id as text) ILIKE :id");
            params.addValue("id", "%" + pattern.getId() + "%");
        }
        if (pattern.getSourceId() != null) {
            values.add(" source_id = :sourceId");
            params.addValue("sourceId",  + pattern.getSourceId());
        }
        if (pattern.getDescription() != null  && !pattern.getDescription().equals("")) {
            values.add(" description ILIKE :description ");
            params.addValue("description", "%" + pattern.getDescription() + "%");
        }
        if (pattern.getDirection() != null  && !pattern.getDirection().equals("")) {
            values.add(" direction ILIKE :direction");
            params.addValue("direction", "%" + pattern.getDirection() + "%");
        }
        if (pattern.getManagement() != null  && !pattern.getManagement().equals("")) {
            values.add(" management ILIKE :management");
            params.addValue("management", "%" + pattern.getManagement() + "%");
        }
        if (pattern.getName() != null  && !pattern.getName().equals("")) {
            values.add(" name ILIKE :name");
            params.addValue("name", "%" + pattern.getName() + "%");
        }
        if (pattern.getFileCount1() != null) {
            values.add(" file_count >= :fileCount1");
            params.addValue("fileCount1", pattern.getFileCount1());
        }
        if (pattern.getFileCount2() != null) {
            values.add(" file_count <= :fileCount2");
            params.addValue("fileCount2", pattern.getFileCount2());
        }
        if (pattern.getFileCount1() != null) {
            values.add(" archive_file_count >= :fileCount1");
            params.addValue("fileCount1", pattern.getArchiveFileCount1());
        }
        if (pattern.getFileCount2() != null) {
            values.add(" archive_file_count <= :fileCount2");
            params.addValue("fileCount2", pattern.getArchiveFileCount2());
        }
        fullRepoHelper.createDataQuery(pattern.getHelpModel());

        values.addAll(fullRepoHelper.getValues());
        params.addValues(fullRepoHelper.getParams().getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }
}
