package ru.iac.ASGIHDTORIS.lib.app;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.CustomConnection;

import java.sql.Connection;

@Component
public interface TestConnection {

    boolean testConnection(CustomConnection connection);

    Connection getConnection(CustomConnection connection);

}
