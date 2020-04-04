package ru.iac.ASGIHDTORIS.spring.component;

import lombok.Data;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Component
public class DataQueryHelper {

    private List<String> values = Collections.emptyList();
    private MapSqlParameterSource params = new MapSqlParameterSource();

    public void createDataQuery(HelpModel helpModel) {
        values = new ArrayList<>();
        params = new MapSqlParameterSource();

        if (helpModel != null) {
            if (helpModel.getDateCreation1() != null) {
                values.add(" date_creation >= :dateCreation1");
                params.addValue("dateCreation1", helpModel.getDateCreation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateCreation2() != null) {
                values.add(" date_creation <= :dateCreation2");
                params.addValue("dateCreation2", helpModel.getDateCreation2().atTime(23, 59, 59));
            }
            if (helpModel.getDateDeactivation1() != null) {
                values.add(" date_deactivation >= :dateDeactivation1");
                params.addValue("dateDeactivation1", helpModel.getDateDeactivation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateDeactivation2() != null) {
                values.add(" date_deactivation <= :dateDeactivation2");
                params.addValue("dateDeactivation2", helpModel.getDateDeactivation2().atTime(23, 59, 59));
            }
            if (helpModel.getDateCreation1() != null) {
                values.add(" date_activation >= :dateActivation1");
                params.addValue("dateActivation1", helpModel.getDateCreation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateActivation2() != null) {
                values.add(" date_activation <= :dateActivation2");
                params.addValue("dateActivation2", helpModel.getDateActivation2().atTime(23, 59, 59));
            }
            if (helpModel.getLastUpdate1() != null) {
                values.add(" last_update >= :lastUpdate1");
                params.addValue("lastUpdate1", helpModel.getLastUpdate1().atTime(0, 0, 0));
            }
            if (helpModel.getLastUpdate2() != null) {
                values.add(" last_update <= :lastUpdate2");
                params.addValue("lastUpdate2", helpModel.getLastUpdate2().atTime(23, 59, 59));
            }
            if (helpModel.getIsArchive() != null) {
                values.add(" archive = :isArchive");
                params.addValue("isArchive", helpModel.getIsArchive());
            }
        }
    }

}
