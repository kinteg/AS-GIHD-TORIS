package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.api.parser.archive.zip.ZipParser;
import ru.iac.ASGIHDTORIS.api.parser.csv.CsvParser;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
public class ParserTest {

    private final String TABLE_NAME = "fgdg";

    @Autowired
    private DataSource dataSource;

    @Test
    void test() throws IOException, CsvValidationException, SQLException {
        File file = new File("/home/kinteg/Загрузки/qwe/qwe2.zip");
        ColumnExporter columnExporter = new PostgreSqlColumnExporter(dataSource.getConnection());
        ParserIntoJson parser = new ParserIntoJson();

        System.out.println(parser.getFromFile(file, 2, columnExporter.exportDataModel(TABLE_NAME), TABLE_NAME).toJSONString());
    }


}
