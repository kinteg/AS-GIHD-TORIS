package ru.iac.ASGIHDTORIS.api.parser.json;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.validator.ColumnValidator;
import ru.iac.ASGIHDTORIS.api.parser.json.validator.Validator;

import java.util.ArrayList;
import java.util.List;

public class ColumnCreator {

    public static List<DataModel> createColumns(List<String> nameColumns) {
        List<DataModel> models = new ArrayList<>();
        Validator validator = new ColumnValidator();

        for (int i = 0; i < nameColumns.size(); i++) {

            String name = nameColumns.get(i).trim();

            if (validator.isValid(name)) {
                name = "default" + i;
            }

            DataModel model;

            if (i == 0) {
                 model = new DataModel(name, true);
            } else {
                 model = new DataModel(name);
            }

            models.add(model);
        }

        return models;
    }

}
