package ru.iac.ASGIHDTORIS.api.db.exporter.column;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class PostgreSqlColumnExporter implements ColumnExporter {

    private final String SQL_SELECT =
            "SELECT ${column_name} " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE table_name = '${table_name}'";

    private static final String COLUMN_NAME_REGEX = "\\$\\{column_name\\}";
    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";

    private final String COLUMN_NAME = "column_name";
    private final String DATA_TYPE = "data_type";

    private Connection connection;

    public PostgreSqlColumnExporter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DataModel> exportDataModel(String tableName) {
        return createDataModel(tableName);
    }

    private List<DataModel> createDataModel(String tableName) {
        List<DataModel> models = new ArrayList<>();
        List<String> columns = getColumnNames(tableName);
        List<String> types = getDataType(tableName);

        for (int i = 0; i < columns.size() && i < types.size(); i++) {
            DataModel model = new DataModel(columns.get(i), types.get(i));
            models.add(model);
        }

        return models;
    }

    private List<String> getColumnNames(String tableName) {
        return parse(createColumnSql(tableName));
    }

    private String createColumnSql(String tableName) {
        return setTableName(tableName).replaceFirst(COLUMN_NAME_REGEX, COLUMN_NAME);
    }

    private List<String> getDataType(String tableName) {
        return parse(createTypeSql(tableName));
    }

    private String createTypeSql(String tableName) {
        return setTableName(tableName).replaceFirst(COLUMN_NAME_REGEX, DATA_TYPE);
    }

    private String setTableName(String tableName) {
        return SQL_SELECT.replaceFirst(TABLE_NAME_REGEX, tableName);
    }

    private List<String> parse(String query) {
        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return list;
    }

}
