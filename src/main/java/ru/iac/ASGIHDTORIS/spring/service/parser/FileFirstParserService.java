package ru.iac.ASGIHDTORIS.spring.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class FileFirstParserService implements FirstParserService {

    private final FileService fileService;

    public FileFirstParserService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<FullTableModel> getFullTable(File file, long limit) {
        if (file == null) {
            return Collections.emptyList();
        }

        List<File> files = fileService.getFiles(file);

        return getFullTableModels(files, limit);
    }


    private List<FullTableModel> getFullTableModels(List<File> files, long limit) {
        List<FullTableModel> fullTableModels = new ArrayList<>();

        for (File file:
                files) {

            FullTableModel fullTableModel;

            try {
                fullTableModel = getFullTableModel(file, limit);
            } catch (Exception e) {
                log.error(e.getMessage());
                fullTableModel = new FullTableModel();
            } finally {
                file.delete();
            }

            fullTableModels.add(fullTableModel);
        }

        return fullTableModels;
    }

    private FullTableModel getFullTableModel(File file, long limit) {
        FileParser fileParser = FileParserFactory.getParser(FileNameUtils.getExtension(file.getName()));

        if (fileParser == null) {
            return new FullTableModel();
        }

        FullTableModel fullTableModel;

        try {
            fullTableModel = fileParser.getFullTable(file, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        } finally {
            file.delete();
        }

        return fullTableModel;
    }



}
