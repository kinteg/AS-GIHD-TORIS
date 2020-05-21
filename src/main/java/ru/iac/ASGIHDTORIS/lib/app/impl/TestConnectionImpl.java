package ru.iac.ASGIHDTORIS.lib.app.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.app.TestConnection;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.CustomConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class TestConnectionImpl implements TestConnection {

    @Override
    public boolean testConnection(CustomConnection customConnection) {

        try {
            Connection connection = DriverManager.getConnection(
                    customConnection.getURL(),
                    customConnection.getUsername(),
                    customConnection.getPassword());

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public Connection getConnection(CustomConnection customConnection) {
        if (testConnection(customConnection)) {
            try {
                return DriverManager.getConnection(
                        customConnection.getURL(),
                        customConnection.getUsername(),
                        customConnection.getPassword());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
