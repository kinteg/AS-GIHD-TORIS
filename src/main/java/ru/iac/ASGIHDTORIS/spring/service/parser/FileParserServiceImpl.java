package ru.iac.ASGIHDTORIS.spring.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreatorFromDb;
import ru.iac.ASGIHDTORIS.common.validator.fileParserValidator.FileParserServiceValidator;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileParserServiceImpl implements FileParserService {

    private final FileService fileService;
    private final TableModelCreatorFromDb tableModelCreatorFromDb;
    private final FileParserServiceValidator fileParserServiceValidator;

    public FileParserServiceImpl(
            FileService fileService,
            TableModelCreatorFromDb tableModelCreatorFromDb,
            FileParserServiceValidator fileParserServiceValidator) {

        this.fileService = fileService;
        this.tableModelCreatorFromDb = tableModelCreatorFromDb;
        this.fileParserServiceValidator = fileParserServiceValidator;
    }

    @Override
    public List<FullTableModel> getFullTable(File file, long limit, long patternId) {
        if (!fileParserServiceValidator.valid(file, limit, patternId)) {
            return Collections.emptyList();
        }

        return createFullTableModel(file, limit, patternId);
    }

    @Override
    public List<FullTableModel> getFullTable(File file, long limit) {
        if (!fileParserServiceValidator.valid(file, limit)) {
            return Collections.emptyList();
        }

        return createFullTableModel(file, limit);
    }

    @Override
    public FullTableModel getFullTable(
            File file, long limit,
            String patternTableName,
            String patternNameFile
    ) {
        if (!fileParserServiceValidator.valid(file, limit, patternTableName, patternNameFile)) {
            return new FullTableModel();
        }

        return createFullTableModel(file, limit, patternTableName, patternNameFile);
    }

    private List<FullTableModel> createFullTableModel(File file, long limit, long patternId) {
        List<TableModel> tableModels = tableModelCreatorFromDb.createTableModels(patternId);
        List<File> files = fileService.getFiles(file);
        List<FullTableModel> fullTableModels = getFullTableModels(files, tableModels, limit);

        file.delete();

        return fullTableModels;
    }

    private List<FullTableModel> createFullTableModel(File file, long limit) {
        List<File> files = fileService.getFiles(file);
        List<FullTableModel> fullTableModels = getFullTableModels(files, limit);

        file.delete();

        return fullTableModels;
    }

    private FullTableModel createFullTableModel(
            File file, long limit,
            String patternTableName, String patternNameFile
    ) {
        file = fileService.getFile(file, patternNameFile);
        FullTableModel fullTableModel = getFullTableModel(file, limit, patternTableName);

        file.delete();

        return fullTableModel;
    }

    private List<FullTableModel> getFullTableModels(List<File> files, List<TableModel> tableModels, long limit) {
        List<FullTableModel> fullTableModels = new ArrayList<>();

        for (int i = 0; i < files.size() && i < tableModels.size(); i++) {
            fullTableModels.add(getFullTableModel(files.get(i), tableModels.get(i), limit));
        }

        return fullTableModels;
    }

    private List<FullTableModel> getFullTableModels(List<File> files, long limit) {
        List<FullTableModel> fullTableModels = new ArrayList<>();

        for (File file :
                files) {

            fullTableModels.add(getFullTableModel(file, limit));
        }

        return fullTableModels;
    }


    private FullTableModel getFullTableModel(File file, long limit, String nameTable) {
        FullTableModel fullTableModel = getFullTableModel(file, limit);
        fullTableModel.getTableModel().setTableName(nameTable);

        file.delete();

        return fullTableModel;
    }

    private FullTableModel getFullTableModel(File file, TableModel tableModel, long limit) {
        FullTableModel fullTableModel;

        try {
            fullTableModel = parseFile(file, tableModel, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        } finally {
            file.delete();
        }

        return fullTableModel;
    }

    private FullTableModel getFullTableModel(File file, long limit) {
        FullTableModel fullTableModel;

        try {
            fullTableModel = parseFile(file, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        } finally {
            file.delete();
        }

        return fullTableModel;
    }

    private FullTableModel parseFile(File file, TableModel tableModel, long limit) {
        FileParser fileParser = FileParserFactory.getParser(FileNameUtils.getExtension(file.getName()));

        FullTableModel fullTableModel;

        try {
            fullTableModel = fileParser.getFullTable(file, limit, tableModel);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        }

        return fullTableModel;
    }

    private FullTableModel parseFile(File file, long limit) {
        FileParser fileParser = FileParserFactory.getParser(FilenameUtils.getExtension(file.getName()));

        FullTableModel fullTableModel;

        try {
            fullTableModel = fileParser.getFullTable(file, limit);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        }

        return fullTableModel;
    }

}