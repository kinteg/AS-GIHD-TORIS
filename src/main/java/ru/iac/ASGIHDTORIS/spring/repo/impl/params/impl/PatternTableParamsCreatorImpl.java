package ru.iac.ASGIHDTORIS.spring.repo.impl.params.impl;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.component.DataQueryHelper;
import ru.iac.ASGIHDTORIS.spring.repo.impl.params.ParamsCreator;

@Component
public class PatternTableParamsCreatorImpl implements ParamsCreator<PatternTableModel> {

    private final DataQueryHelper dataQueryHelper;

    public PatternTableParamsCreatorImpl(
            DataQueryHelper dataQueryHelper) {

        this.dataQueryHelper = dataQueryHelper;
    }

    @Override
    public MapSqlParameterSource makeParams(PatternTableModel pattern) {
        return createParams(pattern);
    }

    private MapSqlParameterSource createParams(PatternTableModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        if (pattern.getId() != null && !pattern.getId().equals("")) {
            params.addValue("id", makeIdParam(pattern));
        }
        if (pattern.getNameTable() != null && !pattern.getNameTable().equals("")) {
            params.addValue("nameTable", makeNameTableParam(pattern));
        }
        if (pattern.getNameFile() != null && !pattern.getNameFile().equals("")) {
            params.addValue("nameFile", makeNameFileParam(pattern));
        }
        if (pattern.getPatternId() != null) {
            params.addValue("patternId", makePatternIdParam(pattern));
        }
        if (pattern.getSourceId() != null) {
            params.addValue("sourceId", makeSourceIdParam(pattern));
        }

        params.addValue("isActive", makeIsActiveParam());

        dataQueryHelper.createDataQuery(pattern.getHelpModel());

        params.addValues(dataQueryHelper.getParams().getValues());

        return params;
    }

    private String makeIdParam(PatternTableModel pattern) {
        return "%" + pattern.getId() + "%";
    }

    private String makeNameTableParam(PatternTableModel pattern) {
        return "%" + pattern.getNameTable() + "%";
    }

    private String makeNameFileParam(PatternTableModel pattern) {
        return "%" + pattern.getNameFile() + "%";
    }

    private String makePatternIdParam(PatternTableModel pattern) {
        return "%" + pattern.getPatternId() + "%";
    }

    private String makeSourceIdParam(PatternTableModel pattern) {
        return "%" + pattern.getSourceId() + "%";
    }

    private boolean makeIsActiveParam() {
        return true;
    }
}
