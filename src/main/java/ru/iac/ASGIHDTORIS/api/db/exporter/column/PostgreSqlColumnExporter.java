package ru.iac.ASGIHDTORIS.api.db.exporter.column;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.db.DataModel;

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
    public List<DataModel> exportDataModel(String tableName) throws SQLException {
        return createDataModel(tableName);
    }

    private List<DataModel> createDataModel(String tableName) throws SQLException {
        List<DataModel> models = new ArrayList<>();
        List<String> columns = getColumnNames(tableName);
        List<String> types = getDataType(tableName);

        for (int i = 0; i < columns.size() && i < types.size(); i++) {
            DataModel model = new DataModel(columns.get(i), types.get(i));
            models.add(model);
        }

        return models;
    }

    private List<String> getColumnNames(String tableName) throws SQLException {
        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            log.info(createColumnSql(tableName));
            ResultSet resultSet = stmt.executeQuery(createColumnSql(tableName));
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return list;
    }

    private List<String> getDataType(String tableName) throws SQLException {
        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            log.info(createColumnSql(tableName));
            ResultSet resultSet = stmt.executeQuery(createTypeSql(tableName));
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        return list;
    }

    private String createColumnSql(String tableName) {
        return setTableName(tableName).replaceFirst(COLUMN_NAME_REGEX, COLUMN_NAME);
    }

    private String createTypeSql(String tableName) {
        return setTableName(tableName).replaceFirst(COLUMN_NAME_REGEX, DATA_TYPE);
    }

    private String setTableName(String tableName) {
        return SQL_SELECT.replaceFirst(TABLE_NAME_REGEX, tableName);
    }

}
