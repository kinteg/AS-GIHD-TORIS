package ru.iac.ASGIHDTORIS.spring.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileParserServiceImpl implements FileParserService {

    private final TableModelCreator tableModelCreator;
    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final ColumnExporterRepo columnExporterRepo;
    private final FileService fileService;

    public FileParserServiceImpl(TableModelCreator tableModelCreator, PatternRepo patternRepo, PatternTableRepo patternTableRepo, ColumnExporterRepo columnExporterRepo, FileService fileService) {
        this.tableModelCreator = tableModelCreator;
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.columnExporterRepo = columnExporterRepo;
        this.fileService = fileService;
    }

    @Override
    public List<FullTableModel> getFullTable(File file, long limit, long patternId) {
        List<TableModel> tableModels = createTableModels(patternId);
        List<File> files = fileService.getFiles(file);

        return getFullTableModels(files, tableModels, limit);
    }

    @Override
    public List<FullTableModel> getFullTable(MultipartFile multipartFile, long limit) {
        File file = fileService.convertFile(multipartFile);

        if (file == null) {
            return Collections.emptyList();
        }

        List<File> files = fileService.getFiles(file);

        return getFullTableModels(files, limit);
    }

    @Override
    public FullTableModel getFullTable(MultipartFile multipartFile, long limit,
                                       String patternTableName, String patternNameFile) {

        File file = fileService.convertFile(multipartFile);

        if (file == null) {
            return new FullTableModel();
        }

        file = fileService.getFile(file, patternNameFile);

        return getFullTableModel(file, limit, patternTableName);
    }


    private List<FullTableModel> getFullTableModels(List<File> files, long limit) {
        List<FullTableModel> fullTableModels = new ArrayList<>();

        for (File file :
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


    private FullTableModel getFullTableModel(File file, long limit, String nameTable) {
        FullTableModel fullTableModel = getFullTableModel(file, limit);
        fullTableModel.getTableModel().setTableName(nameTable);

        file.delete();

        return fullTableModel;
    }

    private FullTableModel getFullTableModel(File file, long limit) {
        FileParser fileParser = FileParserFactory.getParser(FilenameUtils.getExtension(file.getName()));

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


    private List<TableModel> createTableModels(long patternId) {
        Pattern pattern = patternRepo.findById(patternId);
        List<PatternTable> patternTables;

        if (pattern != null) {
            patternTables = patternTableRepo.findAllByPatternIdAndIsActive(pattern.getId(), true);
        } else {
            patternTables = Collections.emptyList();
        }

        List<List<DataModel>> modelList = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            modelList.add(columnExporterRepo.exportDataModel(patternTable.getNameTable()));
            tableNames.add(patternTable.getNameTable());
            fileNames.add(patternTable.getNameFile());
        }

        try {
            columnExporterRepo.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        tableModelCreator.setTableModel(fileNames, tableNames, modelList);

        return tableModelCreator.getTableModel();
    }

    private List<FullTableModel> getFullTableModels(List<File> files, List<TableModel> tableModels, long limit) {
        List<FullTableModel> fullTableModels = new ArrayList<>();

        for (int i = 0; i < files.size() && i < tableModels.size(); i++) {

            FullTableModel fullTableModel;

            try {
                fullTableModel = getFullTableModel(files.get(i), tableModels.get(i), limit);
            } catch (Exception e) {
                log.error(e.getMessage());
                fullTableModel = new FullTableModel();
            } finally {
                files.get(i).delete();
            }

            fullTableModels.add(fullTableModel);
        }


        return fullTableModels;
    }

    private FullTableModel getFullTableModel(File file, TableModel tableModel, long limit) {
        FileParser fileParser = FileParserFactory.getParser(FileNameUtils.getExtension(file.getName()));

        if (fileParser == null) {
            return new FullTableModel();
        }

        FullTableModel fullTableModel;

        try {
            fullTableModel = fileParser.getFullTable(file, limit, tableModel);
        } catch (Exception e) {
            log.error(e.getMessage());
            fullTableModel = new FullTableModel();
        } finally {
            file.delete();
        }

        return fullTableModel;
    }

}