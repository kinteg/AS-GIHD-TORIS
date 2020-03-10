package ru.iac.ASGIHDTORIS.api.parser.json.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;
import ru.iac.ASGIHDTORIS.api.parser.json.JsonCreator;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.CsvReaderImpl;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CsvParser implements FileParser {

    @Override
    public JSONObject getJSON(File file) throws Exception {
        return getJSON(file, CUSTOM_LIMIT);
    }

    @Override
    public JSONObject getJSON(File file, long limit) throws Exception {
        return getJSON(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models) throws Exception {
        return getJSON(file, limit, models, "default");
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws Exception {
        return csvReader(file, limit, models, tableName);
    }

    private JSONObject csvReader(File file, long limit, List<DataModel> models, String tableName) throws Exception {
        Reader reader = createReader(file);

        if (models.isEmpty()) {
            models = getNamesColumn(reader);
        }

        JsonCreator creator = new JsonCreator(reader);

        return creator.createJson(file.getName(), models, limit, tableName);
    }

    private Reader createReader(File file) throws FileNotFoundException {
        return new CsvReaderImpl(new CSVReader(new FileReader(file)));
    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        String[] nameColumn = reader.readNext();
        List<DataModel> models = new ArrayList<>();

        for (String column :
                nameColumn) {

            DataModel model = new DataModel(column);
            models.add(model);
        }

        return models;
    }

}