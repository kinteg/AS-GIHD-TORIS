package ru.iac.ASGIHDTORIS.lib.lib.db.exporter.impl;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.TableExporter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TableExporterImpl implements TableExporter {

    private final Connection connection;
    private final ColumnExporterRepo columnExporterRepo;

    public TableExporterImpl(Connection connection) {
        this.connection = connection;
        columnExporterRepo = new PostgreSqlColumnExporterRepo(connection);
    }

    @Override
    public FullTableModel tableExporter(String tableName, int limit) {
        List<DataModel> dataModels = columnExporterRepo.exportDataModel(tableName);

        TableModel tableModel = TableModel
                .builder()
                .models(dataModels)
                .tableName(tableName)
                .primaryKey(dataModels.stream().filter(DataModel::isPrimary)
                        .findFirst().orElse(DataModel.createEmptyDataModel()).getKey())
                .build();

        return FullTableModel
                .builder()
                .tableModel(tableModel)
                .values(getValues(tableName, limit))
                .build();
    }

    private List<Map<String, String>> getValues(String tableName, int limit) {
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement
                    .executeQuery("SELECT * FROM " + tableName + " LIMIT " + limit);

            return resultSetToList(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private List<Map<String, String>> resultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<Map<String, String>> rows = new ArrayList<>();

        while (rs.next()) {
            Map<String, String> row = new HashMap<>(columns);

            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getString(i));
            }

            rows.add(row);
        }

        return rows;
    }

}
