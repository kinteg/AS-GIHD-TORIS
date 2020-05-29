package ru.iac.ASGIHDTORIS.spring.repo.impl.params.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;

@Component
public class PatternParamsCreatorImpl implements ParamsCreator<PatternModel> {

    private final DataQueryHelper dataQueryHelper;

    public PatternParamsCreatorImpl(
            DataQueryHelper dataQueryHelper) {

        this.dataQueryHelper = dataQueryHelper;
    }

    @Override
    public MapSqlParameterSource makeParams(PatternModel pattern) {
        return createParams(pattern);
    }

    private MapSqlParameterSource createParams(PatternModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (pattern.getId() != null && !pattern.getId().equals("")) {
            params.addValue("id", makeIdParam(pattern));
        }
        if (pattern.getSourceId() != null) {
            params.addValue("sourceId", makeSourceIdParam(pattern));
        }
        if (pattern.getDescription() != null && !pattern.getDescription().equals("")) {
            params.addValue("description", makeDescriptionParam(pattern));
        }
        if (pattern.getDirection() != null && !pattern.getDirection().equals("")) {
            params.addValue("direction", makeDirectionNameParam(pattern));
        }
        if (pattern.getManagement() != null && !pattern.getManagement().equals("")) {
            params.addValue("management", makeManagementParam(pattern));
        }
        if (pattern.getName() != null && !pattern.getName().equals("")) {
            params.addValue("name", makeNameParam(pattern));
        }
        if (pattern.getFileCount1() != null) {
            params.addValue("fileCount1", makeFileCount1Param(pattern));
        }
        if (pattern.getFileCount2() != null) {
            params.addValue("fileCount2", makeFileCount2Param(pattern));
        }
        if (pattern.getArchiveFileCount1() != null) {
            params.addValue("archiveFileCount1", makeArchiveFileCount1TypeParam(pattern));
        }
        if (pattern.getArchiveFileCount2() != null) {
            params.addValue("archiveFileCount2", makeArchiveFileCount2TypeParam(pattern));
        }

        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        params.addValues(dataQueryHelper.getParams().getValues());

        return params;
    }

    private String makeIdParam(PatternModel pattern) {
        return "%" + pattern.getId() + "%";
    }

    private String makeSourceIdParam(PatternModel pattern) {
        return pattern.getSourceId().toString();
    }

    private String makeDescriptionParam(PatternModel pattern) {
        return "%" + pattern.getDescription() + "%";
    }

    private String makeDirectionNameParam(PatternModel pattern) {
        return "%" + pattern.getDirection() + "%";
    }

    private String makeManagementParam(PatternModel pattern) {
        return "%" + pattern.getManagement() + "%";
    }

    private String makeNameParam(PatternModel pattern) {
        return "%" + pattern.getName() + "%";
    }

    private String makeFileCount1Param(PatternModel pattern) {
        return "%" + pattern.getFileCount1() + "%";
    }

    private String makeFileCount2Param(PatternModel pattern) {
        return "%" + pattern.getFileCount2() + "%";
    }

    private String makeArchiveFileCount1TypeParam(PatternModel pattern) {
        return "%" + pattern.getArchiveFileCount1() + "%";
    }

    private String makeArchiveFileCount2TypeParam(PatternModel pattern) {
        return "%" + pattern.getArchiveFileCount2() + "%";
    }
}
