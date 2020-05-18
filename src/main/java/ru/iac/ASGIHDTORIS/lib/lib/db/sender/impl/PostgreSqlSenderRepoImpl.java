package ru.iac.ASGIHDTORIS.lib.lib.db.sender.impl;

import org.apache.commons.collections.CollectionUtils;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.db.sender.SenderRepo;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PostgreSqlSenderRepoImpl implements SenderRepo {

    private static final String SQL_INSERT =
            "INSERT INTO ${table} (${keys}) VALUES (${values}) ";

    private static final String SQL_UPDATE =
            "ON CONFLICT (${id}) DO UPDATE SET ${keys_values}";

    private static final String TABLE_REGEX = "\\$\\{table}";
    private static final String KEYS_REGEX = "\\$\\{keys}";
    private static final String VALUES_REGEX = "\\$\\{values}";
    private static final String ID_REGEX = "\\$\\{id}";
    private static final String KEYS_VALUES_REGEX = "\\$\\{keys_values}";

    private final Connection connection;

    public PostgreSqlSenderRepoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(Reader reader, TableModel tableModel) {
        return createExecuteQuery(reader, tableModel.getTableName(), tableModel.getModels());
    }

    private boolean createExecuteQuery(Reader reader, String tableName, List<DataModel> keys) {

            if (reader == null) {
                return false;
            }

            executeFirstRow(reader, tableName, keys);
            executeAll(reader, tableName, keys);

        return true;
    }

    private void executeFirstRow(Reader reader, String tableName, List<DataModel> keys) {
        List<String> nextRecord;

        if (!(nextRecord = reader.readNext()).isEmpty()
                && !CollectionUtils.containsAny(nextRecord,
                keys.stream().map(DataModel::getKey).collect(Collectors.toList()))
        ) {
            String query = createSql(tableName, keys, nextRecord);
            execute(query);
        }
    }

    private void executeAll(Reader reader, String tableName, List<DataModel> keys) {
        List<String> nextRecord;

        while (!(nextRecord = reader.readNext()).isEmpty()) {
            String query = createSql(tableName, keys, nextRecord);
            execute(query);
        }
    }

    private void execute(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException ignored) {

        }
    }

    private String createSql(String tableName, List<DataModel> keys, List<String> values) {

        String insert = createInsert(tableName, keys, values);
        String update = createUpdate(keys, values);

        return insert + update;
    }

    private String createInsert(String tableName, List<DataModel> keys, List<String> values) {
        String insert = SQL_INSERT;

        int size = Integer.min(keys.size(), values.size());

        insert = insert.replaceFirst(TABLE_REGEX, tableName);
        insert = insert.replaceFirst(KEYS_REGEX, createKey(keys, size));
        insert = insert.replaceFirst(VALUES_REGEX, createValue(values, size));

        return insert;
    }

    private String createUpdate(List<DataModel> keys, List<String> values) {
        String update = SQL_UPDATE;

        String id = createId(keys);
        if (id == null || id.equals("")) {
            return "";
        }
        update = update.replaceFirst(ID_REGEX, createId(keys));
        update = update.replaceFirst(KEYS_VALUES_REGEX, createKeyAndValue(keys, values));

        return update;
    }

    private String createValue(List<String> values, int size) {
        List<String> values1 = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            values1.add("'" + values.get(i).trim() + "'");
        }

        return String.join(", ", values1);
    }

    private String createKey(List<DataModel> models, int size) {
        List<String> keys = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            keys.add(models.get(i).getKey());
        }

        return String.join(", ", keys);
    }

    private String createKeyAndValue(List<DataModel> keys, List<String> values) {
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < keys.size() && i < values.size(); i++) {

            if (keys.get(i).isPrimary()) {
                continue;
            }

            strings.add(
                    keys.get(i).getKey() +
                            " = " +
                            "'" +
                            values.get(i) +
                            "'"
            );

        }

        return String.join(",", strings);
    }

    private String createId(List<DataModel> keys) {
        return keys
                .stream()
                .filter(DataModel::isPrimary)
                .map(DataModel::getKey)
                .collect(Collectors.joining(","));
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
