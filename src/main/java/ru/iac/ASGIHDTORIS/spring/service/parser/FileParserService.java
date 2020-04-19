package ru.iac.ASGIHDTORIS.spring.service.parser;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;

import java.io.File;
import java.util.List;

@Service
public interface FileParserService {

    List<FullTableModel> getFullTable(File file, long limit, long patternId);

    List<FullTableModel> getFullTable(File file, long limit);

    FullTableModel getFullTable(
            File file, long limit,
            String patternNameTable,
            String patternNameFile
    );

}
