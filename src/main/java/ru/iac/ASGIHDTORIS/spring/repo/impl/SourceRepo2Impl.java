package ru.iac.ASGIHDTORIS.spring.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;
import ru.iac.ASGIHDTORIS.spring.component.SourceCountMapper;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo2;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class SourceRepo2Impl implements SourceRepo2 {

    private final Mapper<List<Source>> sourceMapper;
    private final SourceCountMapper sourceCountMapper;
    private final FullRepoHelper<Source> fullRepoHelper;

    public SourceRepo2Impl(@Qualifier("sourceMapper") Mapper<List<Source>> sourceMapper, SourceCountMapper sourceCountMapper, FullRepoHelper<Source> fullRepoHelper) {
        this.sourceMapper = sourceMapper;
        this.sourceCountMapper = sourceCountMapper;
        this.fullRepoHelper = fullRepoHelper;
    }

    @Override
    public Page<Source> findAllSourceByQuery(Pageable pageable, SourceModel source) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        String valueQuery = createQueryValue(source, params);
        String pageQuery = fullRepoHelper.createPageQuery(pageable, source.getSort(), source.getKey());

        List<Source> sources = fullRepoHelper.getAll(valueQuery, pageQuery, params, sourceMapper);
        int count = fullRepoHelper.getCount(valueQuery, params, sourceCountMapper);

        return new PageImpl<>(sources, pageable, count);
    }

    private String createQueryValue(SourceModel source, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (source.getId() != null  && !source.getId().equals("")) {
            values.add(" cast(id as text) ILIKE :id");
            params.addValue("id", "%" + source.getId() + "%");
        }
        if (source.getName() != null  && !source.getName().equals("")) {
            values.add(" name ILIKE :name ");
            params.addValue("name", "%" + source.getName() + "%");
        }
        if (source.getLongName() != null  && !source.getLongName().equals("")) {
            values.add(" long_name ILIKE :longName");
            params.addValue("longName", "%" + source.getLongName() + "%");
        }
        if (source.getShortName() != null  && !source.getShortName().equals("")) {
            values.add(" short_name ILIKE :shortName");
            params.addValue("shortName", "%" + source.getShortName() + "%");
        }
        if (source.getDescription() != null  && !source.getDescription().equals("")) {
            values.add(" description ILIKE :description");
            params.addValue("description", "%" + source.getDescription() + "%");
        }
        if (source.getAddDescription() != null  && !source.getAddDescription().equals("")) {
            values.add(" add_description ILIKE :addDescription");
            params.addValue("addDescription", "%" + source.getAddDescription() + "%");
        }
        if (source.getScope() != null  && !source.getScope().equals("")) {
            values.add(" scope ILIKE :scope");
            params.addValue("scope", "%" + source.getScope() + "%");
        }
        if (source.getPeriodicity() != null  && !source.getPeriodicity().equals("")) {
            values.add(" periodicity ILIKE :periodicity");
            params.addValue("periodicity", "%" + source.getPeriodicity() + "%");
        }
        if (source.getRenewalPeriod() != null  && !source.getRenewalPeriod().equals("")) {
            values.add(" renewal_period ILIKE :renewalPeriod");
            params.addValue("renewalPeriod", "%" + source.getRenewalPeriod() + "%");
        }
        if (source.getType() != null  && !source.getType().equals("")) {
            values.add(" type ILIKE :type");
            params.addValue("type", "%" + source.getType() + "%");
        }
        if (source.getTags() != null  && !source.getTags().equals("")) {
            values.add(" tags ILIKE :tags");
            params.addValue("tags", "%" + source.getTags() + "%");
        }
        if (source.getProviderLink() != null  && !source.getProviderLink().equals("")) {
            values.add(" provider_link ILIKE :providerLink");
            params.addValue("providerLink", "%" + source.getProviderLink() + "%");
        }
        if (source.getDataSource() != null  && !source.getDataSource().equals("")) {
            values.add(" data_source ILIKE :dataSource");
            params.addValue("dataSource", "%" + source.getDataSource() + "%");
        }
        fullRepoHelper.createDataQuery(source.getDateModel());

        values.addAll(fullRepoHelper.getValues());
        params.addValues(fullRepoHelper.getParams().getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }

}
