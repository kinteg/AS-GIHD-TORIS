package ru.iac.ASGIHDTORIS.api.parser.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.FileParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CsvParser implements FileParser {

    private CSVReader reader;
    private final long CUSTOM_LIMIT = 20;
    private final long WITHOUT_LIMIT = -1;

    @Override
    public JSONObject getJSON(File file) throws IOException, CsvValidationException {
        return getJSON(file, CUSTOM_LIMIT);
    }

    @Override
    public JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException {
        return isNull(file)
                ? new JSONObject()
                : getJSON(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models) throws IOException, CsvValidationException {
        return getJSON(file, limit, models, "");
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws IOException, CsvValidationException {
        return csvReader(file, limit, models, "default");
    }


    private boolean isNull(File file) {
        return file == null;
    }

    private JSONObject csvReader(File file, long limit, List<DataModel> models, String tableName) throws IOException, CsvValidationException {
        reader = new CSVReader(new FileReader(file));

        if (models.isEmpty()) {
            models = getNamesColumn();
        }

        return createJson(file.getName(), models, limit, tableName);
    }

    private List<DataModel> getNamesColumn() throws IOException, CsvValidationException {
        String[] nameColumn = reader.readNext();
        List<DataModel> models = new ArrayList<>();

        for (String column :
                nameColumn) {

            DataModel model = new DataModel(column);
            models.add(model);
        }

        return models;
    }

    private JSONObject createJson(String filename, List<DataModel> models, long limit, String tableName) throws IOException, CsvValidationException {
        JSONObject parsed = new JSONObject();
        JSONArray columnTable = getColumnTable(models);
        JSONArray values;

        if (limit == WITHOUT_LIMIT) {
            values = getWithoutLimit(models);
        } else {
            values = getWithLimit(models, limit);
        }

        parsed.put("nameTable", filename);
        parsed.put("nameFile", tableName);
        parsed.put("table", values);
        parsed.put("columnTable", columnTable);

        return parsed;
    }

    private JSONArray getColumnTable(List<DataModel> models) {
        JSONArray array = new JSONArray();

        for (DataModel model :
                models) {
            array.add(getColumnData(model));
        }

        return array;
    }

    private JSONObject getColumnData(DataModel model) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", model.getKey());
        jsonObject.put("type", model.getType());
        jsonObject.put("primary", model.isPrimary());

        return jsonObject;
    }

    private JSONArray getWithoutLimit(List<DataModel> models) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        while ((nextRecord = reader.readNext()) != null) {
            array.add(getJsonObject(models, nextRecord));
        }

        return array;
    }

    private JSONArray getWithLimit(List<DataModel> models, long limit) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        for (int j = 0; (nextRecord = reader.readNext()) != null && j < limit; j++) {
            array.add(getJsonObject(models, nextRecord));
        }

        return array;
    }

    private JSONObject getJsonObject(List<DataModel> models, String[] record) {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < models.size() && i < record.length; i++) {
            jsonObject.put(models.get(i).getKey().trim(), record[i].trim());
        }

        return jsonObject;
    }
}