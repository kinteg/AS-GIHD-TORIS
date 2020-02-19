package ru.iac.ASGIHDTORIS.api.parser.db;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ParseJsonInBd {

    private Connection connection;
    List<String> types;
    List<String> names;
    List<Map<String, String>> values;
    String tableName;
    String table;
    String tableInfo;
    private final String PRIMARY_KEY = "PRIMARY KEY ";
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ";
    private final String INSERT = "INSERT INTO ";
    private final String CONFLICT = "ON CONFLICT ";
    private final String UPDATE = "DO UPDATE SET ";

    public ParseJsonInBd(Connection connection) {
        this.connection = connection;
    }

    public boolean push(String table, String tableInfo) {
        this.table = table;
        this.tableInfo = tableInfo;
        createTable();

        return insertJsonInBd();
    }

    private boolean insertJsonInBd() {

        try (Statement stmt = connection.createStatement()) {
            for (Map<String, String> map :
                    values) {
                stmt.executeUpdate(createInsertSql(map));
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }

        return true;
    }

    private String createInsertSql(Map<String, String> map) {
        StringBuilder sql = new StringBuilder(INSERT);

        sql.append(tableName).append("(");

        sql.append(String.join(", ", names));

        sql.append(") ");

        sql.append("VALUES ").append("( ");

        List<String> list = new ArrayList<>(map.values());

        sql.append(list.stream().map((v) -> "'" + v + "'").collect(Collectors.joining(", ")));
        sql.append(") ");
        sql.append(CONFLICT);
        sql.append("(").append(names.get(0)).append(") ");
        sql.append(UPDATE);

        for (int i = 1; i < names.size(); i++) {
            sql.append(names.get(i)).append(" = '").append(list.get(i)).append("' ");
            if (i != names.size() - 1) {
                sql.append(", ");
            }
        }

        return sql.toString();
    }

    private boolean createTable() {
        try (Statement stmt = connection.createStatement()) {
            return stmt.execute(getCreateTableSql());
        } catch (SQLException exc) {
            exc.printStackTrace();
            return false;
        }
    }

    private String getCreateTableSql() {
        StringBuilder sql = new StringBuilder(CREATE_TABLE);

        initParams();

        sql.append(tableName).append(" ").append("(");

        for (int i = 0; i < types.size(); i++) {
            sql.append(names.get(i)).append(" ")
                    .append(types.get(i)).append(" ");
            if (i == 0) {
                sql.append(PRIMARY_KEY);
            }
            if (i != types.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(")");

        return sql.toString();
    }

    private void initParams() {
        types = JsonPath.read(tableInfo, "$.content.columnTable[*].type");
        names = JsonPath.read(tableInfo, "$.content.columnTable[*].name");
        tableName = JsonPath.read(tableInfo, "$.content.nameTable");
        values = JsonPath.read(table, "$.content[0].table[*]");
    }

}
