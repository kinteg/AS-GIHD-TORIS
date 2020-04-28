package ru.iac.ASGIHDTORIS.spring.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
public class PostgreSqlColumnExporterRepo implements ColumnExporterRepo {

    private final String SQL_SELECT =
            "SELECT ${column_name} " +
                    "FROM INFORMATION_SCHEMA.COLUMNS " +
                    "WHERE table_name = '${table_name}'";

    private final String SQL_P_KEY =
            "SELECT ${column_name} " +
                    "FROM INFORMATION_SCHEMA.key_column_usage " +
                    "WHERE table_name = '${table_name}' AND constraint_name = '${table_name}_pkey'";

    private final String COLUMN_NAME_REGEX = "\\$\\{column_name}";
    private final String TABLE_NAME_REGEX = "\\$\\{table_name}";

    private final String COLUMN_NAME = "column_name";
    private final String DATA_TYPE = "data_type";

    private Connection connection;

    public PostgreSqlColumnExporterRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<DataModel> exportDataModel(String tableName) {
        return createDataModel(tableName);
    }

    private List<DataModel> createDataModel(String tableName) {
        List<DataModel> models = new ArrayList<>();
        List<String> columns = getColumnNames(tableName.toLowerCase());
        List<String> types = getDataType(tableName.toLowerCase());
        List<String> keys = getPKey(tableName.toLowerCase());

        for (int i = 0; i < columns.size() && i < types.size(); i++) {

            DataModel model = new DataModel(
                    columns.get(i),
                    types.get(i),
                    keys.contains(columns.get(i)));

            models.add(model);
        }

        return models;
    }

    private List<String> getColumnNames(String tableName) {
        return parse(createColumnSql(tableName));
    }

    private String createColumnSql(String tableName) {
        return setTableName(tableName, SQL_SELECT).replaceFirst(COLUMN_NAME_REGEX, COLUMN_NAME);
    }

    private List<String> getDataType(String tableName) {
        return parse(createTypeSql(tableName));
    }

    private String createTypeSql(String tableName) {
        return setTableName(tableName, SQL_SELECT).replaceFirst(COLUMN_NAME_REGEX, DATA_TYPE);
    }

    private List<String> getPKey(String tableName) {
        return parse(createPKeySql(tableName));
    }

    private String createPKeySql(String tableName) {
        return setTableName(tableName, SQL_P_KEY).replaceFirst(COLUMN_NAME_REGEX, COLUMN_NAME);
    }

    private String setTableName(String tableName, String sql) {
        return sql.replaceAll(TABLE_NAME_REGEX, tableName);
    }

    private List<String> parse(String query) {
        List<String> list = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }

        return list;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
