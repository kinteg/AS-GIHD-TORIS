package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.PatternTableMapper;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PatternTableRepo2Impl implements PatternTableRepo2 {

    private final String SELECT_QUERY = "SELECT * FROM pattern_table";
    private final String COUNT_QUERY = "SELECT count(*) FROM pattern_table";

    private final PatternTableMapper patternTableMapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<PatternTable> fullRepoHelper;
    private final DataQueryHelper dataQueryHelper;

    public PatternTableRepo2Impl(PatternTableMapper patternTableMapper, CountMapper countMapper, FullRepoHelper<PatternTable> fullRepoHelper, DataQueryHelper dataQueryHelper) {
        this.patternTableMapper = patternTableMapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
        this.dataQueryHelper = dataQueryHelper;
    }

    @Override
    public Page<PatternTable> findAllSourceByQuery(Pageable pageable, PatternTableModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(pattern, params);
        String pageQuery = fullRepoHelper.createPageQuery(pageable, pattern.getHelpModel().getSort(), pattern.getHelpModel().getKey(), "id");

        String selectQuery = SELECT_QUERY + valueQuery + pageQuery;
        String countQuery = COUNT_QUERY + valueQuery;

        List<PatternTable> patterns = fullRepoHelper.getAll(selectQuery, params, patternTableMapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

        return new PageImpl<>(patterns, pageable, count);
    }

    private String createQueryValue(PatternTableModel pattern, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null && !pattern.getId().equals("")) {
            values.add(" cast(id as text) ILIKE :id");
            params.addValue("id", "%" + pattern.getId() + "%");
        }
        if (pattern.getNameTable() != null && !pattern.getNameTable().equals("")) {
            values.add(" name_table ILIKE :nameTable");
            params.addValue("nameTable", "%" + pattern.getNameTable() + "%");
        }
        if (pattern.getNameFile() != null && !pattern.getNameFile().equals("")) {
            values.add(" name_file ILIKE :nameFile ");
            params.addValue("nameFile", "%" + pattern.getNameFile() + "%");
        }
        if (pattern.getPatternId() != null) {
            values.add(" pattern_id = :patternId");
            params.addValue("patternId", pattern.getPatternId());
        }
        if (pattern.getSourceId() != null) {
            values.add(" source_id = :sourceId");
            params.addValue("sourceId", pattern.getSourceId());
        }
        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        values.add(" active = :isActive");
        params.addValue("isActive", true);

        values.addAll(dataQueryHelper.getValues());
        params.addValues(dataQueryHelper.getParams().getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }
}
