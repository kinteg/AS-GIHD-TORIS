package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.PatternTableMapper;
import ru.iac.ASGIHDTORIS.spring.component.SourceCountMapper;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatternTableRepo2Impl implements PatternTableRepo2 {

    private final String NAME = "pattern_table";

    private final PatternTableMapper patternTableMapper;
    private final SourceCountMapper sourceCountMapper;
    private final FullRepoHelper<PatternTable> fullRepoHelper;

    public PatternTableRepo2Impl(PatternTableMapper patternTableMapper, SourceCountMapper sourceCountMapper, FullRepoHelper<PatternTable> fullRepoHelper) {
        this.patternTableMapper = patternTableMapper;
        this.sourceCountMapper = sourceCountMapper;
        this.fullRepoHelper = fullRepoHelper;
    }

    @Override
    public Page<PatternTable> findAllSourceByQuery(Pageable pageable, PatternTableModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(pattern, params);
        String pageQuery = fullRepoHelper.createPageQuery(pageable, pattern.getHelpModel().getSort(), pattern.getHelpModel().getKey());

        List<PatternTable> patterns = fullRepoHelper.getAll(NAME, valueQuery, pageQuery, params, patternTableMapper);
        int count = fullRepoHelper.getCount(NAME, valueQuery, params, sourceCountMapper);

        return new PageImpl<>(patterns, pageable, count);
    }

    private String createQueryValue(PatternTableModel pattern, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null  && !pattern.getId().equals("")) {
            values.add(" cast(id as text) ILIKE :id");
            params.addValue("id", "%" + pattern.getId() + "%");
        }
        if (pattern.getNameTable() != null && !pattern.getNameTable().equals("")) {
            values.add(" name_table = :nameTable");
            params.addValue("nameTable", "%" + pattern.getNameTable() + "%");
        }
        if (pattern.getNameFile() != null  && !pattern.getNameFile().equals("")) {
            values.add(" name_file ILIKE :nameFile ");
            params.addValue("nameFile", "%" + pattern.getNameFile() + "%");
        }
        if (pattern.getPatternId() != null) {
            values.add(" pattern_id = :patternId");
            params.addValue("patternId",  + pattern.getPatternId());
        }
        fullRepoHelper.createDataQuery(pattern.getHelpModel());

        values.addAll(fullRepoHelper.getValues());
        params.addValues(fullRepoHelper.getParams().getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }
}
