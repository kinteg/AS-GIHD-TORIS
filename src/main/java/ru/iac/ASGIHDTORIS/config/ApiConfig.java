package ru.iac.ASGIHDTORIS.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.api.db.creator.Creator;
import ru.iac.ASGIHDTORIS.api.db.creator.PostgreSqlCreator;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.parser.DbDataParser;
import ru.iac.ASGIHDTORIS.api.db.exporter.parser.DbParser;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModelCreatorImpl;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModelCreatorImpl;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.db.sender.FileSender;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.FilesParser;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
@Slf4j
public class ApiConfig {

    @Bean
    public Parser api() {
        return new FilesParser();
    }

    @Bean
    public Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean
    public DbParser getDbParser(Connection connection) {
        return new DbDataParser(connection);
    }

    @Bean
    public ColumnExporter getColumnExporter(Connection connection) {
        return new PostgreSqlColumnExporter(connection);
    }

    @Bean
    public DataModelCreator getDataModelCreator() {
        return new DataModelCreatorImpl();
    }

    @Bean
    public TableModelCreator getTableModelCreator() {
        return new TableModelCreatorImpl();
    }

    @Bean
    public DataSender getDataSender(Connection connection) {
        return new FileSender(connection);
    }

    @Bean
    public Creator getCreator(Connection connection) {
        return new PostgreSqlCreator(connection);
    }

}
