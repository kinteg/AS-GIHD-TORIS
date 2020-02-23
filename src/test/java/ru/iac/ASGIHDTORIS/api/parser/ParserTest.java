package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.data.DataExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.data.PostgreSqlDataExporter;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ParserTest {

    private final String TABLE_NAME1 = "fgdg";
    private final String TABLE_NAME2 = "test001";
    private List<String> TABLE_NAMES = new ArrayList<>(Arrays.asList(TABLE_NAME1, TABLE_NAME2));

    @Autowired
    private DataSource dataSource;

    @Test
    void test() throws IOException, CsvValidationException, SQLException {
        File file = new File("/home/kinteg/Загрузки/qwe/qwe2.zip");
        ColumnExporter columnExporter = new PostgreSqlColumnExporter(dataSource.getConnection());
        FilesParser parser = new FilesParser();

//        System.out.println(parser.getFromFile(file, 2, columnExporter.exportDataModel(TABLE_NAME1), TABLE_NAME1).toJSONString());
    }

    @Test
    void test2() throws SQLException {
        DataExporter exporter = new PostgreSqlDataExporter(dataSource.getConnection());
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        for (String table :
                TABLE_NAMES) {
            jsonArray.add(exporter.exportData(table, 2));
        }

        jsonObject.put("content", jsonArray);

        System.out.println(jsonObject.toString());
    }


}
