package ru.iac.ASGIHDTORIS.api.db.loader;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.postgresql.SqlInsertCreator;
import ru.iac.ASGIHDTORIS.api.factory.loader.LoaderFactory;
import ru.iac.ASGIHDTORIS.api.parser.reader.FileReader;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class LoaderImpl implements Loader {

    private Connection connection;
    private SqlInsertCreator createSql;
    private List<List<String>> allRecord;
    private List<String> firstRecord;

    public LoaderImpl(Connection connection) {
        this.connection = connection;
        this.createSql = new SqlInsertCreator();
    }

    @Override
    public boolean insert(File file, String tableName, List<DataModel> keys) {
        return executeQuery(file, tableName, keys);
    }

    private boolean executeQuery(File file, String tableName, List<DataModel> keys) {
        createExecuteQuery(file);

        executeFirstRow(tableName, keys);
        executeAllRows(tableName, keys);

        return true;
    }

    private void createExecuteQuery(File file) {
        try (FileReader reader = LoaderFactory.getParser(file)) {
            allRecord = reader.getAll();
            firstRecord = reader.getFirst();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void executeFirstRow(String tableName, List<DataModel> keys) {
        log.info(firstRecord.get(0));
        if (!keys.stream().map(DataModel::getKey).collect(Collectors.toList()).contains(firstRecord.get(0))) {
            String query = createSql.createSql(tableName, keys, allRecord.get(0));
            execute(query);
        }
    }

    private void executeAllRows(String tableName, List<DataModel> keys) {
        for (int i = 1; i < allRecord.size(); i++) {
            String query = createSql.createSql(tableName, keys, allRecord.get(i));
            execute(query);
        }
    }

    private void execute(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            log.error(query);
            log.error(e.getMessage());
        }
    }

}
