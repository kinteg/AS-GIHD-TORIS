package ru.iac.ASGIHDTORIS.api.db.exporter.data;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class PostgreSqlDataExporter implements DataExporter {

    private final String SQL_SELECT =
            "SELECT * " +
                    "FROM ${table_name} " +
                    "LIMIT ${limit}";

    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";
    private static final String LIMIT_REGEX = "\\$\\{limit\\}";

    private Connection connection;
    private ColumnExporter columnExporter;

    public PostgreSqlDataExporter(Connection connection) {
        this(connection, new PostgreSqlColumnExporter(connection));
    }

    public PostgreSqlDataExporter(Connection connection, ColumnExporter columnExporter) {
        this.connection = connection;
        this.columnExporter = columnExporter;
    }

    @Override
    public JSONObject exportData(String tableName, long limit) {
        return createExportData(tableName, limit);
    }

    private JSONObject createExportData(String tableName, long limit) {
        return createJson(tableName, limit, getModel(tableName));
    }

    private List<DataModel> getModel(String tableName) {
        return columnExporter.exportDataModel(tableName);
    }

    private JSONObject createJson(String tableName, long limit, List<DataModel> models) {
        JSONObject parsed = new JSONObject();

        JSONArray columnTable = getColumnTable(models);
        JSONArray values;

        values = getValue(limit, tableName, models);

        parsed.put("nameTable", tableName);
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


    private JSONArray getValue(long limit, String tableName, List<DataModel> models) {
        JSONArray array = new JSONArray();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(createQuery(limit, tableName));
            while (resultSet.next()) {
                array.add(createRow(resultSet, models));
            }
        } catch (SQLException e) {
            return new JSONArray();
        }

        return array;

    }

    private String createQuery(long limit, String tableName) {
        String query = SQL_SELECT.replaceFirst(TABLE_NAME_REGEX, tableName);
        query = query.replaceFirst(LIMIT_REGEX, String.valueOf(limit));

        return query;
    }

    private JSONObject createRow(ResultSet resultSet, List<DataModel> models) throws SQLException {
        JSONObject row = new JSONObject();

        for (DataModel model :
                models) {
            row.put(model.getKey(), resultSet.getString(model.getKey()));
        }

        log.error("createRow: " + row.toJSONString());

        return row;
    }

}
