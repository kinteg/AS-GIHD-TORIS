package ru.iac.ASGIHDTORIS;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;

import java.io.File;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class FileParserTest {

    @Test
    public void getJSON() throws Exception {

        File file = new File("/home/kinteg/Загрузки/qwe/OKS.csv");

        FileParser parser = FileParserFactory.getParser(FilenameUtils.getExtension(file.getName()));

        String str = parser.getFullTable(file, 15).getValues().toString();

        assertNotNull(str);

        System.out.println(str);

    }

}
