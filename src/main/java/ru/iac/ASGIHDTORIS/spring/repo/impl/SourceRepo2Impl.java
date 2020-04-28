package ru.iac.ASGIHDTORIS.spring.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo2;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl.QueryCreator;

import java.util.List;

@Repository
@Slf4j
public class SourceRepo2Impl implements SourceRepo2 {

    private final Mapper<List<Source>> sourceMapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<Source> fullRepoHelper;
    private final QueryCreator<SourceModel> queryCreator;
    private final ParamsCreator<SourceModel> paramsCreator;

    public SourceRepo2Impl(
            @Qualifier("sourceMapper") Mapper<List<Source>> sourceMapper,
            CountMapper countMapper,
            FullRepoHelper<Source> fullRepoHelper,
            @Qualifier("sourceQueryCreatorImpl") QueryCreator<SourceModel> queryCreator,
            @Qualifier("sourceParamsCreatorImpl") ParamsCreator<SourceModel> paramsCreator) {

        this.sourceMapper = sourceMapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
        this.queryCreator = queryCreator;
        this.paramsCreator = paramsCreator;
    }

    @Override
    public Page<Source> findAllSourceByQuery(Pageable pageable, SourceModel source) {

        queryCreator.makeQuery(source, pageable);

        String selectQuery = queryCreator.getSelectQuery();
        String countQuery = queryCreator.getCountQuery();

        MapSqlParameterSource params = paramsCreator.makeParams(source);

        List<Source> sources = fullRepoHelper.getAll(selectQuery, params, sourceMapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

        return new PageImpl<>(sources, pageable, count);
    }

}
