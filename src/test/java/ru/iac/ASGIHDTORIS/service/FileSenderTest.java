package ru.iac.ASGIHDTORIS.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.sender.FileSenderService;

import java.io.File;
import java.util.List;

@SpringBootTest
public class FileSenderTest {

    @Autowired
    private FileSenderService fileSenderService;
    @Autowired
    private PatternTableRepo patternTableRepo;

    @Test
    public void sendFiles() {
        File file = new File("/home/kinteg/Загрузки/qwe/qwe/арабская ночь2.zip");
        List<PatternTable> patternTables = patternTableRepo.findAllByPatternId(21);

        fileSenderService.sendFiles(patternTables, file);
    }

}
