package ru.iac.ASGIHDTORIS.spring.service.dataChecker.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusCreator;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;
import ru.iac.ASGIHDTORIS.common.validator.checkDataValidator.DataCheckValidator;
import ru.iac.ASGIHDTORIS.lib.app.UnArchiver;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.SimpleFileParser;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.dataChecker.DataCheckerService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataCheckerServiceImpl implements DataCheckerService {

    private final PatternTableRepo patternTableRepo;
    private final ColumnExporterRepo columnExporterRepo;
    private final FileStatusCreator fileStatusCreator;
    private final DataCheckValidator dataCheckValidator;
    private final UnArchiver unArchiver;
    private final SimpleFileParser simpleFileParser;

    public DataCheckerServiceImpl(PatternTableRepo patternTableRepo, ColumnExporterRepo columnExporterRepo, FileStatusCreator fileStatusCreator, DataCheckValidator dataCheckValidator, UnArchiver unArchiver, SimpleFileParser simpleFileParser) {
        this.patternTableRepo = patternTableRepo;
        this.columnExporterRepo = columnExporterRepo;
        this.fileStatusCreator = fileStatusCreator;
        this.dataCheckValidator = dataCheckValidator;
        this.unArchiver = unArchiver;
        this.simpleFileParser = simpleFileParser;
    }

    @Override
    public List<FileStatusModel> checkDates(File file, Long id) {
        return generateFileStatusModels(file, id);
    }

    @Override
    public FileStatusModel checkData(File file, Long id) {
        return generateFileStatusModel(file, id);
    }

    private List<FileStatusModel> generateFileStatusModels(File file, Long id) {
        if (dataCheckValidator.checkByPatternId(file, id)) {
            return getFileStatusModels(file, id);
        } else {
            return null;
        }
    }

    private List<FileStatusModel> getFileStatusModels(File file, Long id) {
        List<PatternTable> patternTables = patternTableRepo
                .findAllByPatternIdAndIsArchiveAndIsActive(id, false, true);

        List<FileStatusModel> fileStatusModels = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {
            fileStatusModels.add(generateFileStatusModel(file, patternTable.getId()));
        }

        return fileStatusModels;
    }

    private FileStatusModel generateFileStatusModel(File file, Long id) {
        if (dataCheckValidator.checkByPatternTableId(file, id)) {
            return getFileStatusModel(file, id);
        } else {
            return null;
        }
    }

    private FileStatusModel getFileStatusModel(File file, Long id) {
        PatternTable patternTable = patternTableRepo.findById((long) id);
        File targetFile = unArchiver.unArchiveFile(file, patternTable.getNameFile());

        return createFileStatusModel(targetFile, patternTable);
    }

    private FileStatusModel createFileStatusModel(File targetFile, PatternTable patternTable) {
        FileStatusModel fileStatusModel;

        if (targetFile == null) {
            fileStatusModel = createFileStatusModelNotFound(patternTable);
        } else {
            fileStatusModel = createFileStatusModelOtherStatus(targetFile, patternTable);
            targetFile.delete();
        }

        return fileStatusModel;
    }

    private FileStatusModel createFileStatusModelNotFound(PatternTable patternTable) {
        return fileStatusCreator.getFileNotFoundStatusModel(patternTable.getNameFile(), patternTable.getNameTable());
    }

    private FileStatusModel createFileStatusModelOtherStatus(File targetFile, PatternTable patternTable) {
        int fileColumnSize = getFileCountSize(targetFile);
        int tableColumnSize = columnExporterRepo.exportDataModel(patternTable.getNameTable()).size();

        return fileStatusCreator.getFileStatusModel(tableColumnSize, fileColumnSize,
                patternTable.getNameFile(), patternTable.getNameTable());
    }

    private int getFileCountSize(File targetFile) {
        return simpleFileParser.getFullTable(targetFile).getModels().size();
    }
}
