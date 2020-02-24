package ru.iac.ASGIHDTORIS.api.db.loader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CSVLoader implements Loader{

    private final String SQL_INSERT =
            "INSERT INTO ${table} (${keys}) VALUES (${values}) ";

    private final String SQL_UPDATE =
            "ON CONFLICT (${id}) DO UPDATE SET ${keys_values}";

    private final String TABLE_REGEX = "\\$\\{table\\}";
    private final String KEYS_REGEX = "\\$\\{keys\\}";
    private final String VALUES_REGEX = "\\$\\{values\\}";
    private final String ID_REGEX = "\\$\\{id\\}";
    private final String KEYS_VALUES_REGEX = "\\$\\{keys_values\\}";

    private Connection connection;

    public CSVLoader(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean insert(File file, String tableName, List<DataModel> keys) {
        return createExecuteQuery(file, tableName, keys);
    }

    private boolean createExecuteQuery(File file, String tableName, List<DataModel> keys) {

        try (CSVReader reader = createReader(file)) {
            List<String> nextRecord;

            reader.skip(1);

            while (!(nextRecord = nextRecord(reader)).isEmpty()) {
                String query = createSql(tableName, keys, nextRecord);
                execute(query);
            }

        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    private boolean execute(String query) {

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    private CSVReader createReader(File file) throws FileNotFoundException {
        return new CSVReader(new FileReader(file));
    }

    private List<String> nextRecord(CSVReader reader) throws IOException, CsvValidationException {
        String[] records = reader.readNext();
        return records == null ?
                Collections.emptyList()
                : Arrays.asList(records);
    }

    private String createSql(String tableName, List<DataModel> keys, List<String> values) {

        String insert = createInsert(tableName, keys, values);
        String update = createUpdate(keys, values);

        return insert + update;
    }

    private String createInsert(String tableName, List<DataModel> keys, List<String> values) {
        String insert = SQL_INSERT;

        insert = insert.replaceFirst(TABLE_REGEX, tableName);
        insert = insert.replaceFirst(KEYS_REGEX, createKey(keys));
        insert = insert.replaceFirst(VALUES_REGEX, createValue(values));

        return insert;
    }

    private String createUpdate(List<DataModel> keys, List<String> values) {
        String update = SQL_UPDATE;

        update = update.replaceFirst(ID_REGEX, createId(keys));
        update = update.replaceFirst(KEYS_VALUES_REGEX, createKeyAndValue(keys, values));

        return update;
    }

    private String createValue(List<String> values) {
        return values
                .stream()
                .map((v) -> "'" + v.trim() + "'")
                .collect(Collectors.joining(", "));
    }

    private String createKey(List<DataModel> models) {
        List<String> keys = new ArrayList<>();

        for (DataModel key :
                models) {
            keys.add(key.getKey());
        }

        return String.join(", ", keys);
    }

    private String createKeyAndValue(List<DataModel> keys, List<String> values) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {

            if (keys.get(i).isPrimary()) {
                continue;
            }

            builder
                    .append(keys.get(i).getKey())
                    .append(" = ")
                    .append("'")
                    .append(values.get(i))
                    .append("'");

            if (i != keys.size() - 1) {
                builder.append(", ");
            }

        }

        return builder.toString();
    }

    private String createId(List<DataModel> keys) {
        return keys
                .stream()
                .filter(DataModel::isPrimary)
                .map(DataModel::getKey)
                .collect(Collectors.joining(","));
    }

}
