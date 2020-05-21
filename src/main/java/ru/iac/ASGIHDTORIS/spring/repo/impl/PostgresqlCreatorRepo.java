package ru.iac.ASGIHDTORIS.spring.repo.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.Status;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.spring.repo.CreatorRepo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
@Repository
public class PostgresqlCreatorRepo implements CreatorRepo {

    private static final String SQL_CREATE =
            "CREATE TABLE IF NOT EXISTS ${table_name} (${keys})";

    private static final String TABLE_NAME_REGEX = "\\$\\{table_name}";
    private static final String KEYS_REGEX = "\\$\\{keys}";

    private static final String PK = "PRIMARY KEY";

    private final Connection connection;

    public PostgresqlCreatorRepo(Connection connection) {
        this.connection = connection;
    }

    @Override
    public TableModelStatus createTable(TableModel tableModel) {
        String query = createSql(tableModel);

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            return TableModelStatus
                    .builder()
                    .status(Status.OK)
                    .exception("")
                    .answer("Таблица создана")
                    .tableModel(tableModel)
                    .build();

        } catch (SQLException e) {

            log.warn(e.getMessage() + "\n       " + query);
            return TableModelStatus
                    .builder()
                    .status(Status.ERROR)
                    .exception(e.getMessage())
                    .answer("Таблица не создана")
                    .tableModel(TableModel.emptyTableModel())
                    .build();
        }
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
