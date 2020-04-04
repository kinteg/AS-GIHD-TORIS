package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.PatternMapper;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatternRepo2Impl implements PatternRepo2 {

    private final String SELECT_QUERY = "SELECT * FROM pattern";
    private final String COUNT_QUERY = "SELECT count(*) FROM pattern";

    private final PatternMapper patternMapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<Pattern> fullRepoHelper;
    private final DataQueryHelper dataQueryHelper;

    public PatternRepo2Impl(PatternMapper patternMapper, CountMapper countMapper, FullRepoHelper<Pattern> fullRepoHelper, DataQueryHelper dataQueryHelper) {
        this.patternMapper = patternMapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
        this.dataQueryHelper = dataQueryHelper;
    }

    @Override
    public Page<Pattern> findAllSourceByQuery(Pageable pageable, PatternModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(pattern, params);
        String pageQuery = fullRepoHelper.createPageQuery(pageable, pattern.getHelpModel().getSort(), pattern.getHelpModel().getKey(), "id");

        String selectQuery = SELECT_QUERY + valueQuery + pageQuery;
        String countQuery = COUNT_QUERY + valueQuery;

        List<Pattern> patterns = fullRepoHelper.getAll(selectQuery, params, patternMapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

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
        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        values.addAll(dataQueryHelper.getValues());
        params.addValues(dataQueryHelper.getParams().getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }
}
