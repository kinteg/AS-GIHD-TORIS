package ru.iac.ASGIHDTORIS.spring.repo.impl.query.Data.impl;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.page.PageQueryCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.Data.QueryCreator;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PatternTableQueryCreatorImpl implements QueryCreator<PatternTableModel> {

    private final String SELECT_QUERY = "SELECT * FROM pattern_table";
    private final String COUNT_QUERY = "SELECT count(*) FROM pattern_table";

    private String countQuery;
    private String selectQuery;

    private final DataQueryHelper dataQueryHelper;
    private final PageQueryCreator pageQueryCreator;

    public PatternTableQueryCreatorImpl(
            DataQueryHelper dataQueryHelper,
            PageQueryCreator pageQueryCreator) {

        this.dataQueryHelper = dataQueryHelper;
        this.pageQueryCreator = pageQueryCreator;
    }

    @Override
    public void makeQuery(PatternTableModel pattern, Pageable pageable) {
        String bodyQuery = makeBodyQuery(pattern);

        makeSelectQuery(pattern, pageable, bodyQuery);
        makeCountQuery(bodyQuery);
    }

    private String makeBodyQuery(PatternTableModel pattern) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null && !pattern.getId().equals("")) {
            values.add(makeIdQuery());
        }
        if (pattern.getNameTable() != null && !pattern.getNameTable().equals("")) {
            values.add(makeNameTableQuery());
        }
        if (pattern.getNameFile() != null && !pattern.getNameFile().equals("")) {
            values.add(makeNameFileQuery());
        }
        if (pattern.getPatternId() != null) {
            values.add(makePatternIdQuery());
        }
        if (pattern.getSourceId() != null) {
            values.add(makeSourceIdQuery());
        }

        values.add(makeIsActiveQuery());

        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        values.addAll(dataQueryHelper.getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }

    private String makeIdQuery() {
        return " cast(id as text) ILIKE :id";
    }

    private String makeNameTableQuery() {
        return " name_table ILIKE :nameTable";
    }

    private String makeNameFileQuery() {
        return "  name_file ILIKE :nameFile ";
    }

    private String makePatternIdQuery() {
        return " pattern_id = :patternId";
    }

    private String makeSourceIdQuery() {
        return " source_id = :sourceId";
    }

    private String makeIsActiveQuery() {
        return " active = :isActive";
    }

    private void makeSelectQuery(PatternTableModel pattern, Pageable pageable, String bodyQuery) {
        selectQuery = SELECT_QUERY + bodyQuery + makePageQuery(pattern, pageable);
    }

    private String makePageQuery(PatternTableModel pattern, Pageable pageable) {
        return pageQueryCreator.createPageQuery(
                pageable,
                pattern.getHelpModel().getSort(),
                pattern.getHelpModel().getKey(),
                "id"
        );
    }

    private void makeCountQuery(String bodyQuery) {
        countQuery = COUNT_QUERY + bodyQuery;
    }

}
