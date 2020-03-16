package ru.iac.ASGIHDTORIS.api.parser.json;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.Reader;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class JsonCreator {

    private final long WITHOUT_LIMIT = -1;
    private final Reader reader;

    public JsonCreator(Reader reader) {
        this.reader = reader;
    }

    public JSONObject createJson(String fileName, List<DataModel> models, long limit, String tableName) throws Exception {
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

    private JSONArray getWithoutLimit(List<DataModel> models) throws Exception {
        JSONArray array = new JSONArray();
        List<String> nextRecordArray;

        while ((nextRecordArray = reader.readNext()) != null) {
            array.add(getJsonObject(models.stream().map(DataModel::getKey).collect(Collectors.toList()), nextRecordArray));
        }

        return array;
    }

    private JSONArray getWithLimit(List<DataModel> models, long limit) throws Exception {
        JSONArray array = new JSONArray();
        List<String> nextRecordArray;

        for (int j = 0; (nextRecordArray = reader.readNext()) != null && j < limit; j++) {
            array.add(getJsonObject(models.stream().map(DataModel::getKey).collect(Collectors.toList()), nextRecordArray));
        }

        return array;
    }

    private JSONObject getJsonObject(List<String> keys, List<String> record) {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < keys.size() && i < record.size(); i++) {
            jsonObject.put(keys.get(i).trim(), record.get(i).trim());
        }

        return jsonObject;
    }

}
