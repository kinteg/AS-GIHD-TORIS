package ru.iac.ASGIHDTORIS.spring.repo.impl.params.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;

@Component
public class SourceParamsCreatorImpl implements ParamsCreator<SourceModel> {

    private final DataQueryHelper dataQueryHelper;

    public SourceParamsCreatorImpl(
            DataQueryHelper dataQueryHelper) {

        this.dataQueryHelper = dataQueryHelper;
    }

    @Override
    public MapSqlParameterSource makeParams(SourceModel source) {
        return createParams(source);
    }

    private MapSqlParameterSource createParams(SourceModel source) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (source.getId() != null && !source.getId().equals("")) {
            params.addValue("id", makeIdParam(source));
        }
        if (source.getName() != null && !source.getName().equals("")) {
            params.addValue("name", makeNameParam(source));
        }
        if (source.getLongName() != null && !source.getLongName().equals("")) {
            params.addValue("longName", makeLongNameParam(source));
        }
        if (source.getShortName() != null && !source.getShortName().equals("")) {
            params.addValue("shortName", makeShortNameParam(source));
        }
        if (source.getDescription() != null && !source.getDescription().equals("")) {
            params.addValue("description", makeDescriptionParam(source));
        }
        if (source.getAddDescription() != null && !source.getAddDescription().equals("")) {
            params.addValue("addDescription", makeAddDescriptionParam(source));
        }
        if (source.getScope() != null && !source.getScope().equals("")) {
            params.addValue("scope", makeScopeParam(source));
        }
        if (source.getPeriodicity() != null && !source.getPeriodicity().equals("")) {
            params.addValue("periodicity", makePeriodicityParam(source));
        }
        if (source.getRenewalPeriod() != null && !source.getRenewalPeriod().equals("")) {
            params.addValue("renewalPeriod", makeRenewalPeriodParam(source));
        }
        if (source.getType() != null && !source.getType().equals("")) {
            params.addValue("type", makeTypeParam(source));
        }
        if (source.getTags() != null && !source.getTags().equals("")) {
            params.addValue("tags", makeTagsParam(source));
        }
        if (source.getProviderLink() != null && !source.getProviderLink().equals("")) {
            params.addValue("providerLink", makeProviderLinkParam(source));
        }
        if (source.getDataSource() != null && !source.getDataSource().equals("")) {
            params.addValue("dataSource", makeDataSourceParam(source));
        }

        dataQueryHelper.createDataQuery(source.getHelpModel());

        params.addValues(dataQueryHelper.getParams().getValues());

        return params;
    }

    private String makeIdParam(SourceModel source) {
        return "%" + source.getId() + "%";
    }

    private String makeNameParam(SourceModel source) {
        return "%" + source.getName() + "%";
    }

    private String makeLongNameParam(SourceModel source) {
        return "%" + source.getLongName() + "%";
    }

    private String makeShortNameParam(SourceModel source) {
        return "%" + source.getShortName() + "%";
    }

    private String makeDescriptionParam(SourceModel source) {
        return "%" + source.getDescription() + "%";
    }

    private String makeAddDescriptionParam(SourceModel source) {
        return "%" + source.getAddDescription() + "%";
    }

    private String makeScopeParam(SourceModel source) {
        return "%" + source.getScope() + "%";
    }

    private String makePeriodicityParam(SourceModel source) {
        return "%" + source.getPeriodicity() + "%";
    }

    private String makeRenewalPeriodParam(SourceModel source) {
        return "%" + source.getRenewalPeriod() + "%";
    }

    private String makeTypeParam(SourceModel source) {
        return "%" + source.getType() + "%";
    }

    private String makeTagsParam(SourceModel source) {
        return "%" + source.getTags() + "%";
    }

    private String makeProviderLinkParam(SourceModel source) {
        return "%" + source.getProviderLink() + "%";
    }

    private String makeDataSourceParam(SourceModel source) {
        return "%" + source.getDataSource() + "%";
    }

}
