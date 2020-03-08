package ru.iac.ASGIHDTORIS.api.parser.json.txt;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtParser implements FileParser {


    private BufferedReader reader;

    @Override
    public JSONObject getJSON(File file) throws IOException {
        return getJSON(file, CUSTOM_LIMIT);
    }

    @Override
    public JSONObject getJSON(File file, long limit) throws IOException {
        return isNull(file)
                ? new JSONObject()
                : getJSON(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models) throws IOException {
        return getJSON(file, limit, models, "default");
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws IOException {
        return txtReader(file, limit, models, tableName);
    }

    private boolean isNull(File file) {
        return file == null;
    }

    private JSONObject txtReader(File file, long limit, List<DataModel> models, String tableName) throws IOException {
        reader = Files.newBufferedReader(Paths.get(file.getName()), Charset.forName("windows-1251"));


        if (models.isEmpty()) {
            models = getNamesColumn();
        }

        return createJson(file.getName(), models, limit, tableName);
    }

    private List<DataModel> getNamesColumn() throws IOException {
        String[] nameColumn = reader.readLine().split(">");
        log.info(Arrays.toString(nameColumn) + " " + nameColumn.length);

        List<DataModel> models = new ArrayList<>();

        for (String column :
                nameColumn) {

            DataModel model = new DataModel(column);
            models.add(model);
        }

        return models;
    }

    private JSONObject createJson(String fileName, List<DataModel> models, long limit, String tableName) throws IOException {
        JSONObject parsed = new JSONObject();
        JSONArray columnTable = getColumnTable(models);
        JSONArray values;

        if (limit == WITHOUT_LIMIT) {
            values = getWithoutLimit(models);
        } else {
            values = getWithLimit(models, limit);
        }

        parsed.put("nameTable", tableName);
        parsed.put("nameFile", fileName);
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

    private JSONArray getWithoutLimit(List<DataModel> models) throws IOException {
        JSONArray array = new JSONArray();
        String[] nextRecordArray;
        String nextRecord;

        while ((nextRecord = reader.readLine()) != null) {
            nextRecordArray = nextRecord.split(">");
            array.add(getJsonObject(models, nextRecordArray));
        }

        return array;
    }

    private JSONArray getWithLimit(List<DataModel> models, long limit) throws IOException {
        JSONArray array = new JSONArray();
        String[] nextRecordArray;
        String nextRecord;

        for (int j = 0; (nextRecord = reader.readLine()) != null && j < limit; j++) {
//            log.info(nextRecord);
            nextRecordArray = nextRecord.split(">");
            array.add(getJsonObject(models, nextRecordArray));
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
