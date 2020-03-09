package ru.iac.ASGIHDTORIS.api.db.postgresql;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SqlInsertCreator {

    String SQL_INSERT =
            "INSERT INTO ${table} (${keys}) VALUES (${values}) ";

    String SQL_UPDATE =
            "ON CONFLICT (${id}) DO UPDATE SET ${keys_values}";

    String TABLE_REGEX = "\\$\\{table\\}";
    String KEYS_REGEX = "\\$\\{keys\\}";
    String VALUES_REGEX = "\\$\\{values\\}";
    String ID_REGEX = "\\$\\{id\\}";
    String KEYS_VALUES_REGEX = "\\$\\{keys_values\\}";

    public String createSql(String tableName, List<DataModel> keys, List<String> values) {

        String insert = createInsert(tableName, keys, values);
        String update = createUpdate(keys, values);

        return insert + update;
    }

    private String createInsert(String tableName, List<DataModel> keys, List<String> values) {
        String insert = SQL_INSERT;

        int limit = Math.min(keys.size(), values.size());

        insert = insert.replaceFirst(TABLE_REGEX, tableName);
        insert = insert.replaceFirst(KEYS_REGEX, createKey(keys, limit));
        insert = insert.replaceFirst(VALUES_REGEX, createValue(values, limit));

        return insert;
    }

    private String createUpdate(List<DataModel> keys, List<String> values) {
        String update = SQL_UPDATE;

        update = update.replaceFirst(ID_REGEX, createId(keys));
        update = update.replaceFirst(KEYS_VALUES_REGEX, createKeyAndValue(keys, values));

        return update;
    }

    private String createValue(List<String> values, int limit) {
        return values
                .stream()
                .map((v) -> "'" + v.trim() + "'")
                .limit(limit)
                .collect(Collectors.joining(", "));
    }

    private String createKey(List<DataModel> models, int limit) {
        List<String> keys = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            keys.add(models.get(i).getKey());
        }

        return String.join(", ", keys);
    }

    private String createKeyAndValue(List<DataModel> keys, List<String> values) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < keys.size() && i < values.size(); i++) {

            if (keys.get(i).isPrimary()) {
                continue;
            }

            builder
                    .append(keys.get(i).getKey())
                    .append(" = ")
                    .append("'")
                    .append(values.get(i))
                    .append("'");

            if (i != keys.size() - 1 && i != values.size() - 1) {
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
