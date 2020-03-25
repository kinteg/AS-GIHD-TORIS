package ru.iac.ASGIHDTORIS.db.exporter.data;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class PostgreSqlDataExporter implements DataExporter {

    private final String SQL_SELECT =
            "SELECT * " +
                    "FROM ${table_name} " +
                    "ORDER BY ${name_column} ${sort}" +
                    "LIMIT ${limit}" +
                    "OFFSET ${offset}";

    private final String SQL_COUNT =
            "SELECT count(*) " +
                    "FROM ${table_name} ";

    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";
    private static final String LIMIT_REGEX = "\\$\\{limit\\}";
    private static final String NAME_COLUMN = "\\$\\{name_column\\}";
    private static final String SORT = "\\$\\{sort\\}";
    private static final String OFFSET = "\\$\\{offset\\}";

    private Connection connection;

    public PostgreSqlDataExporter(Connection connection) {
        this.connection = connection;
    }

    @Override
    public FullTableModelPage exportData(TableModel tableModel, Pageable pageable) {
        return getFullTableModelPage(tableModel, pageable, tableModel.getModels().get(0).getKey());
    }

    @Override
    public FullTableModelPage exportData(TableModel tableModel, Pageable pageable, String nameColumn) {
        return getFullTableModelPage(tableModel, pageable, nameColumn);
    }


    private FullTableModelPage getFullTableModelPage(TableModel tableModel, Pageable pageable, String nameColumn) {

        Page<Map<String, String>> pages = createValues(tableModel, pageable, nameColumn);

        return FullTableModelPage
                .builder()
                .tableModel(tableModel)
                .values(pages)
                .build();

    }

    private Page<Map<String, String>> createValues(TableModel tableModel, Pageable pageable, String nameColumn) {
        List<Map<String, String>> values = new ArrayList<>();
        int count;
        String tableName = tableModel.getTableName();

        try (Statement stmt = connection.createStatement()) {
            count = stmt.executeQuery(createQuery(tableName)).getRow();
            ResultSet resultSet = stmt.executeQuery(createQuery(tableName, pageable, nameColumn));
            while (resultSet.next()) {
                values.add(createRow(resultSet, tableModel.getModels()));
            }
        } catch (SQLException e) {
            return Page.empty(pageable);
        }

        return new PageImpl<>(values, pageable, count);
    }

    private String createQuery(String tableName, Pageable pageable, String nameColumn) {
        String query = SQL_SELECT.replaceFirst(TABLE_NAME_REGEX, tableName);
        query = query.replaceFirst(NAME_COLUMN, String.valueOf(nameColumn));
        query = query.replaceFirst(SORT, String.valueOf(pageable.getSort()));
        query = query.replaceFirst(LIMIT_REGEX, String.valueOf(pageable.getPageSize()));
        query = query.replaceFirst(OFFSET, String.valueOf(pageable.getPageNumber() * pageable.getPageSize()));

        return query;
    }

    private String createQuery(String tableName) {
        return SQL_COUNT.replaceFirst(TABLE_NAME_REGEX, tableName);
    }

    private Map<String, String> createRow(ResultSet resultSet, List<DataModel> models) throws SQLException {
        Map<String, String> value = new LinkedHashMap<>();

        for (DataModel model :
                models) {
            value.put(model.getKey(), resultSet.getString(model.getKey()));
        }

        return value;
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
