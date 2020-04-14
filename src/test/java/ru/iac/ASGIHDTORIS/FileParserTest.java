package ru.iac.ASGIHDTORIS;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.spring.service.patterTable.PatternTableService;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
public class FileParserTest {

    @Autowired
    private PatternTableService patternTableService;

    @Test
    public void getJSON() throws Exception {

        File file = new File("/home/kinteg/Загрузки/qwe/OKS.csv");

        FileParser parser = FileParserFactory.getParser(FilenameUtils.getExtension(file.getName()));

        String str = parser.getFullTable(file, 15).getValues().toString();

        assertNotNull(str);

        System.out.println(str);

    }

    @Test
    public void patternTableTest() {


        DataModelList dataModelList = DataModelList
                .builder()
                .names(Arrays.asList("number", "budget", "reportperiod", "indicator", "bud_value", "value", "implpercent", "fff"))
                .types(Arrays.asList("integer", "text", "text", "text", "double precision", "double precision", "double precision", "text"))
                .primaries(Arrays.asList(true, false, false, false, false, false, false, false))
                .build();
        TableModel tableModel = TableModel
                .builder()
                .tableName("dkpldtjupdxhijg")
                .filename("арабская ночь.csv")
                .build();

        System.out.println(patternTableService.updatePatternTable(tableModel, dataModelList, Long.valueOf(33)));
    }

}
