package ru.iac.ASGIHDTORIS.spring.service.parser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class FileParserService implements ParserService {


    private final TableModelCreator tableModelCreator;
    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final ColumnExporter columnExporter;
    private final FileService fileService;

    public FileParserService(TableModelCreator tableModelCreator, PatternRepo patternRepo, PatternTableRepo patternTableRepo, ColumnExporter columnExporter, FileService fileService) {
        this.tableModelCreator = tableModelCreator;
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.columnExporter = columnExporter;
        this.fileService = fileService;
    }

    @Override
    public List<FullTableModel> getFullTable(File file, long limit, long patternId) {
        List<TableModel> tableModels = createTableModels(patternId);
        List<File> files = fileService.getFiles(file);

        return getFullTableModels(files, tableModels, limit);
    }


    private List<TableModel> createTableModels(long patternId) {
        Pattern pattern = patternRepo.findById(patternId);
        List<PatternTable> patternTables;

        if (pattern != null) {
            patternTables = patternTableRepo.findAllByPatternId(pattern.getId());
        } else {
            patternTables = Collections.emptyList();
        }

        List<List<DataModel>> modelList = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            modelList.add(columnExporter.exportDataModel(patternTable.getNameTable()));
            tableNames.add(patternTable.getNameTable());
            fileNames.add(patternTable.getNameFile());
        }

        try {
            columnExporter.close();
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
