package ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.PageQueryCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.QueryCreator;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class SourceQueryCreatorImpl implements QueryCreator<SourceModel> {

    private final String SELECT_QUERY = "SELECT * FROM source";
    private final String COUNT_QUERY = "SELECT count(*) FROM source";

    private String countQuery;
    private String selectQuery;

    private final DataQueryHelper dataQueryHelper;
    private final PageQueryCreator pageQueryCreator;

    public SourceQueryCreatorImpl(
            DataQueryHelper dataQueryHelper,
            PageQueryCreator pageQueryCreator) {

        this.dataQueryHelper = dataQueryHelper;
        this.pageQueryCreator = pageQueryCreator;
    }

    @Override
    public void makeQuery(SourceModel source, Pageable pageable) {
        String bodyQuery = makeBodyQuery(source);

        makeSelectQuery(source, pageable, bodyQuery);
        makeCountQuery(bodyQuery);
    }

    private String makeBodyQuery(SourceModel source) {
        List<String> values = new ArrayList<>();

        if (source.getId() != null && !source.getId().equals("")) {
            values.add(makeIdQuery());
        }
        if (source.getName() != null && !source.getName().equals("")) {
            values.add(makeNameQuery());
        }
        if (source.getLongName() != null && !source.getLongName().equals("")) {
            values.add(makeLongNameQuery());
        }
        if (source.getShortName() != null && !source.getShortName().equals("")) {
            values.add(makeShortNameQuery());
        }
        if (source.getDescription() != null && !source.getDescription().equals("")) {
            values.add(makeDescriptionQuery());
        }
        if (source.getAddDescription() != null && !source.getAddDescription().equals("")) {
            values.add(makeAddDescriptionQuery());
        }
        if (source.getScope() != null && !source.getScope().equals("")) {
            values.add(makeScopeQuery());
        }
        if (source.getPeriodicity() != null && !source.getPeriodicity().equals("")) {
            values.add(makePeriodicityQuery());
        }
        if (source.getRenewalPeriod() != null && !source.getRenewalPeriod().equals("")) {
            values.add(makeRenewalPeriodQuery());
        }
        if (source.getType() != null && !source.getType().equals("")) {
            values.add(makeTypeQuery());
        }
        if (source.getTags() != null && !source.getTags().equals("")) {
            values.add(makeTagsQuery());
        }
        if (source.getProviderLink() != null && !source.getProviderLink().equals("")) {
            values.add(makeProviderLinkQuery());
        }
        if (source.getDataSource() != null && !source.getDataSource().equals("")) {
            values.add(makeDataSourceQuery());
        }
        dataQueryHelper.createDataQuery(source.getHelpModel());

        values.addAll(dataQueryHelper.getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }

    private String makeIdQuery() {
        return " cast(id as text) ILIKE :id";
    }

    private String makeNameQuery() {
        return " cast(name as text) ILIKE :name";
    }

    private String makeLongNameQuery() {
        return " cast(long_name as text) ILIKE :longName";
    }

    private String makeShortNameQuery() {
        return " cast(short_name as text) ILIKE :shortName";
    }

    private String makeDescriptionQuery() {
        return " cast(description as text) ILIKE :description";
    }

    private String makeAddDescriptionQuery() {
        return " cast(add_description as text) ILIKE :addDescription";
    }

    private String makeScopeQuery() {
        return " cast(scope as text) ILIKE :scope";
    }

    private String makePeriodicityQuery() {
        return " cast(periodicity as text) ILIKE :periodicity";
    }

    private String makeRenewalPeriodQuery() {
        return " cast(renewal_period as text) ILIKE :renewalPeriod";
    }

    private String makeTypeQuery() {
        return " cast(type as text) ILIKE :type";
    }

    private String makeTagsQuery() {
        return " cast(tags as text) ILIKE :tags";
    }

    private String makeProviderLinkQuery() {
        return " cast(provider_link as text) ILIKE :providerLink";
    }

    private String makeDataSourceQuery() {
        return " cast(data_source as text) ILIKE :dataSource";
    }

    private void makeSelectQuery(SourceModel source, Pageable pageable, String bodyQuery) {
        selectQuery = SELECT_QUERY + bodyQuery + makePageQuery(source, pageable);
    }

    private String makePageQuery(SourceModel source, Pageable pageable) {
        return pageQueryCreator.createPageQuery(
                pageable,
                source.getHelpModel().getSort(),
                source.getHelpModel().getKey(),
                "id"
        );
    }

    private void makeCountQuery(String bodyQuery) {
        countQuery = COUNT_QUERY + bodyQuery;
    }

}
