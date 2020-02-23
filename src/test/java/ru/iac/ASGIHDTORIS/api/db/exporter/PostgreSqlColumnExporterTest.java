package ru.iac.ASGIHDTORIS.api.db.exporter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;

import javax.sql.DataSource;

import java.sql.SQLException;

@SpringBootTest
class PostgreSqlColumnExporterTest {

    @Autowired
    private DataSource dataSource;

    private final String TABLE_NAME = "fgdg";

    @Test
    void exportDataModel() throws SQLException {
        ColumnExporter columnExporter = new PostgreSqlColumnExporter(dataSource.getConnection());

        System.out.println(columnExporter.exportDataModel(TABLE_NAME));
    }
}