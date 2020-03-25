package ru.iac.ASGIHDTORIS.parser.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.common.validator.string.ColumnValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ColumnCreator {

    private static final RandomStringGenerator generator =
            new RandomStringGenerator
                    .Builder()
                    .withinRange('a', 'z')
                    .build();

    public static List<DataModel> createColumns(List<String> nameColumns) {
        List<DataModel> models = new ArrayList<>();
        Validator validator = new ColumnValidator();

        for (int i = 0; i < nameColumns.size(); i++) {

            String name = nameColumns.get(i).replaceAll("\uFEFF", "").trim();

            if (validator.isValid(name)) {
                name = generator.generate(10);
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
