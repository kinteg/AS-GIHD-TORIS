package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl.QueryCreator;

import java.util.List;

@Repository
public class PatternTableRepo2Impl implements PatternTableRepo2 {

    private final Mapper<List<PatternTable>> patternMapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<PatternTable> fullRepoHelper;
    private final QueryCreator<PatternTableModel> queryCreator;
    private final ParamsCreator<PatternTableModel> paramsCreator;

    public PatternTableRepo2Impl(
            @Qualifier("patternTableMapper") Mapper<List<PatternTable>> patternMapper,
            CountMapper countMapper,
            FullRepoHelper<PatternTable> fullRepoHelper,
            @Qualifier("patternTableQueryCreatorImpl") QueryCreator<PatternTableModel> queryCreator,
            @Qualifier("patternTableParamsCreatorImpl") ParamsCreator<PatternTableModel> paramsCreator) {

        this.patternMapper = patternMapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
        this.queryCreator = queryCreator;
        this.paramsCreator = paramsCreator;
    }

    @Override
    public Page<PatternTable> findAllSourceByQuery(Pageable pageable, PatternTableModel pattern) {
        queryCreator.makeQuery(pattern, pageable);

        String selectQuery = queryCreator.getSelectQuery();
        String countQuery = queryCreator.getCountQuery();

        MapSqlParameterSource params = paramsCreator.makeParams(pattern);

        List<PatternTable> patterns = fullRepoHelper.getAll(selectQuery, params, patternMapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

        return new PageImpl<>(patterns, pageable, count);
    }
}
