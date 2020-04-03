package ru.iac.ASGIHDTORIS.spring.service.parser;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;

import java.io.File;
import java.util.List;

@Service
public interface ParserService {

    List<FullTableModel> getFullTable(File file, long limit, long patternId);

}
