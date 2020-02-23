package ru.iac.ASGIHDTORIS.api.db.exporter.parser;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.exporter.data.DataExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.data.PostgreSqlDataExporter;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class DbDataParser implements DbParser {

    private Connection connection;

    public DbDataParser(Connection connection) {
        this.connection = connection;
    }

    @Override
    public JSONObject getFromDb(String tableName, long limit) {
        return getFromDb(Collections.singletonList(tableName), limit);
    }

    @Override
    public JSONObject getFromDb(List<String> tableNames, long limit) {
        return createJson(tableNames, limit);
    }

    private JSONObject createJson(List<String> tableNames, long limit) {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        for (String tableName :
                tableNames) {
            array.add(getData(tableName, limit));
        }

        jsonObject.put("content", array);

        return jsonObject;
    }

    private JSONObject getData(String tableName, long limit) {
        DataExporter exporter = new PostgreSqlDataExporter(connection);
        return exporter.exportData(tableName, limit);
    }
}
