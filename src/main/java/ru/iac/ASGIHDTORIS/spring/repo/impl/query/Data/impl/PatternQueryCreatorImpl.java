package ru.iac.ASGIHDTORIS.spring.repo.impl.query.Data.impl;

import lombok.Getter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.page.PageQueryCreator;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.Data.QueryCreator;

import java.util.ArrayList;
import java.util.List;

@Component
@Getter
public class PatternQueryCreatorImpl implements QueryCreator<PatternModel> {

    private final String SELECT_QUERY = "SELECT * FROM pattern";
    private final String COUNT_QUERY = "SELECT count(*) FROM pattern";

    private String countQuery;
    private String selectQuery;

    private final DataQueryHelper dataQueryHelper;
    private final PageQueryCreator pageQueryCreator;

    public PatternQueryCreatorImpl(
            DataQueryHelper dataQueryHelper,
            PageQueryCreator pageQueryCreator) {

        this.dataQueryHelper = dataQueryHelper;
        this.pageQueryCreator = pageQueryCreator;
    }

    @Override
    public void makeQuery(PatternModel pattern, Pageable pageable) {
        String bodyQuery = makeBodyQuery(pattern);

        makeSelectQuery(pattern, pageable, bodyQuery);
        makeCountQuery(bodyQuery);
    }

    private String makeBodyQuery(PatternModel pattern) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null && !pattern.getId().equals("")) {
            values.add(makeIdQuery());
        }
        if (pattern.getSourceId() != null) {
            values.add(makeSourceIdQuery());
        }
        if (pattern.getDescription() != null && !pattern.getDescription().equals("")) {
            values.add(makeDescriptionQuery());
        }
        if (pattern.getDirection() != null && !pattern.getDirection().equals("")) {
            values.add(makeDirectionNameQuery());
        }
        if (pattern.getManagement() != null && !pattern.getManagement().equals("")) {
            values.add(makeManagementQuery());
        }
        if (pattern.getName() != null && !pattern.getName().equals("")) {
            values.add(makeNameQuery());
        }
        if (pattern.getFileCount1() != null) {
            values.add(makeFileCount1Query());
        }
        if (pattern.getFileCount2() != null) {
            values.add(makeFileCount2Query());
        }
        if (pattern.getArchiveFileCount1() != null) {
            values.add(makeArchiveFileCount1TypeQuery());
        }
        if (pattern.getArchiveFileCount2() != null) {
            values.add(makeArchiveFileCount2TypeQuery());
        }

        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        values.addAll(dataQueryHelper.getValues());

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }

    private String makeIdQuery() {
        return " cast(id as text) ILIKE :id";
    }

    private String makeSourceIdQuery() {
        return " source_id = :sourceId";
    }

    private String makeDescriptionQuery() {
        return " description ILIKE :description ";
    }

    private String makeDirectionNameQuery() {
        return " direction ILIKE :direction";
    }

    private String makeManagementQuery() {
        return " management ILIKE :management";
    }

    private String makeNameQuery() {
        return " name ILIKE :name";
    }

    private String makeFileCount1Query() {
        return " file_count >= :fileCount1";
    }

    private String makeFileCount2Query() {
        return " file_count <= :fileCount2";
    }

    private String makeArchiveFileCount1TypeQuery() {
        return " archive_file_count >= :archiveFileCount1";
    }

    private String makeArchiveFileCount2TypeQuery() {
        return " archive_file_count <= :archiveFileCount2";
    }

    private void makeSelectQuery(PatternModel pattern, Pageable pageable, String bodyQuery) {
        selectQuery = SELECT_QUERY + bodyQuery + makePageQuery(pattern, pageable);
    }

    private String makePageQuery(PatternModel pattern, Pageable pageable) {
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
