package ru.iac.ASGIHDTORIS.lib.lib.db.exporter.impl;

import lombok.SneakyThrows;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.NameExporterRepo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NameExporterRepoImpl implements NameExporterRepo {

    private static final String SELECT_DATA = "SELECT table_name FROM information_schema.tables\n" +
            "WHERE table_schema NOT IN ('information_schema','pg_catalog');";

    private Connection connection;

    public NameExporterRepoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<String> exportNames() {
        List<String> names = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(SELECT_DATA);
            while (resultSet.next()) {
                names.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }

        return names;
    }

    @SneakyThrows
    @Override
    public void close() throws IOException {
        connection.close();
    }
}
