package ru.iac.ASGIHDTORIS.api.db.creator;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class PostgreSqlCreator implements Creator{

    private final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS ${table_name} (${keys})";

    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";

    private static final String PK = "PRIMARY KEY";

    private Connection connection;

    public PostgreSqlCreator(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createTable(String tableName, List<DataModel> models) {

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql(tableName, models));
        } catch (SQLException e) {
            return false;
        }

        return true;
    }

    private String createSql(String tableName, List<DataModel> models) {

        String query = SQL_CREATE.replaceFirst(TABLE_NAME_REGEX, tableName);
        String keys = keysCreator(models);
        query = query.replaceFirst(KEYS_REGEX, keys);

        return query;
    }

    private String keysCreator(List<DataModel> models) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < models.size(); i++) {
            String item = parseDataModel(models.get(i));
            builder.append(item);

            if (i != models.size() - 1) {
                builder.append(", ");
            }
        }

        return builder.toString();
    }


    private String parseDataModel(DataModel model) {

        String key = model.getKey();
        String type = model.getType();
        String pk = model.isPrimary() ?
                PK : "";

        return key + " " + type + " " + pk;
    }

}
