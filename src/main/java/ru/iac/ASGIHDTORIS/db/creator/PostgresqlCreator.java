package ru.iac.ASGIHDTORIS.db.creator;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public class PostgresqlCreator implements Creator {

    private static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS ${table_name} (${keys})";

    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";
    private static final String KEYS_REGEX = "\\$\\{keys\\}";

    private static final String PK = "PRIMARY KEY";

    private final Connection connection;

    public PostgresqlCreator(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean createTable(TableModel tableModel) {
        String query = createSql(tableModel);
        try (Statement stmt = connection.createStatement()) {
            log.error(query);
            stmt.execute(query);
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error(query);
            return false;
        }

        return true;
    }

    private String createSql(TableModel tableModel) {

        String query = SQL_CREATE.replaceFirst(TABLE_NAME_REGEX, tableModel.getTableName());
        String keys = keysCreator(tableModel.getModels());
        query = query.replaceFirst(KEYS_REGEX, keys);

        return query;
    }

    private String keysCreator(List<DataModel> models) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < models.size(); i++) {
            String item = parseDataModel(models.get(i));
            builder.append(item.trim());

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

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
