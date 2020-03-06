package ru.iac.ASGIHDTORIS.api.db;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class SQLtest {

//    private final String SQL_SELECT =
//            "SELECT ${column_name} " +
//                    "FROM INFORMATION_SCHEMA.key_column_usage " +
//                    "WHERE table_name = '${table_name}' AND constraint_name = 'source_pkey'";;
//
//    private static final String COLUMN_NAME_REGEX = "\\$\\{column_name\\}";
//    private static final String TABLE_NAME_REGEX = "\\$\\{table_name\\}";
//
//    private final String COLUMN_NAME = "column_name";
//    private final String DATA_TYPE = "data_type";
//    private final String CONSTRAINT_OID = "constraint_oid";
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Test
//    public void testConstraint_oid() {
//
//        String tableName = "source";
//
//        List<String> list = new ArrayList<>();
//        String query = SQL_SELECT.replaceFirst(COLUMN_NAME_REGEX, COLUMN_NAME);
//        query = query.replaceFirst(TABLE_NAME_REGEX, tableName);
//
//        try (
//                Connection connection = dataSource.getConnection();
//                Statement statment = connection.createStatement();
//        ){
//
//            ResultSet resultSet = statment.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(resultSet.getString(1));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(list);
//
//        assertTrue(list.size() > 0);
//
//    }

}
