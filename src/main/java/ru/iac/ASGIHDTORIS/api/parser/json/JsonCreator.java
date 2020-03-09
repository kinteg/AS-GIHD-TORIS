package ru.iac.ASGIHDTORIS.api.parser.json;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.Reader;

import java.util.List;

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
        String[] nextRecordArray;
        String nextRecord;

        while ((nextRecord = reader.readLine()) != null) {
            nextRecordArray = nextRecord.split(">");
            array.add(getJsonObject(models, nextRecordArray));
        }

        return array;
    }

    private JSONArray getWithLimit(List<DataModel> models, long limit) throws Exception {
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
