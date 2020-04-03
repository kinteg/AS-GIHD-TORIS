package ru.iac.ASGIHDTORIS.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.iac.ASGIHDTORIS.db.creator.Creator;
import ru.iac.ASGIHDTORIS.db.creator.PostgresqlCreator;
import ru.iac.ASGIHDTORIS.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.db.exporter.data.DataExporter;
import ru.iac.ASGIHDTORIS.db.exporter.data.PostgreSqlDataExporter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DbConfig {

    @Bean
    public Connection connection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();
    }

    @Bean
    public Creator creator(Connection connection) {
        return new PostgresqlCreator(connection);
    }

    @Bean
    public DataExporter dataExporter(Connection connection) {
        return new PostgreSqlDataExporter(connection);
    }

}
