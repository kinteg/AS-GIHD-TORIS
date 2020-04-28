package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl.QueryCreator;

import java.util.List;

@Repository
public class PatternRepo2Impl implements PatternRepo2 {

    private final Mapper<List<Pattern>> patternMapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<Pattern> fullRepoHelper;
    private final QueryCreator<PatternModel> queryCreator;
    private final ParamsCreator<PatternModel> paramsCreator;

    public PatternRepo2Impl(
            @Qualifier("patternMapper") Mapper<List<Pattern>> patternMapper,
            CountMapper countMapper,
            FullRepoHelper<Pattern> fullRepoHelper,
            @Qualifier("patternQueryCreatorImpl") QueryCreator<PatternModel> queryCreator,
            @Qualifier("patternParamsCreatorImpl") ParamsCreator<PatternModel> paramsCreator) {

        this.patternMapper = patternMapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
        this.queryCreator = queryCreator;
        this.paramsCreator = paramsCreator;
    }

    @Override
    public Page<Pattern> findAllSourceByQuery(Pageable pageable, PatternModel pattern) {
        queryCreator.makeQuery(pattern, pageable);

        String selectQuery = queryCreator.getSelectQuery();
        String countQuery = queryCreator.getCountQuery();

        MapSqlParameterSource params = paramsCreator.makeParams(pattern);

        List<Pattern> patterns = fullRepoHelper.getAll(selectQuery, params, patternMapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

        return new PageImpl<>(patterns, pageable, count);
    }
}
