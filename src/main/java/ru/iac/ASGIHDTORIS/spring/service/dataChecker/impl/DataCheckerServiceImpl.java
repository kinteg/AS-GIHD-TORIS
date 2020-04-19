package ru.iac.ASGIHDTORIS.spring.service.dataChecker.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusCreator;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;
import ru.iac.ASGIHDTORIS.common.validator.checkDataValidator.DataCheckValidator;
import ru.iac.ASGIHDTORIS.parser.file.fixer.ColumnCreator;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.dataChecker.DataCheckerService;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataCheckerServiceImpl implements DataCheckerService {

    private final FileService fileService;
    private final PatternTableRepo patternTableRepo;
    private final ColumnExporterRepo columnExporterRepo;
    private final FileStatusCreator fileStatusCreator;
    private final DataCheckValidator dataCheckValidator;

    public DataCheckerServiceImpl(FileService fileService, PatternTableRepo patternTableRepo, ColumnExporterRepo columnExporterRepo, FileStatusCreator fileStatusCreator, DataCheckValidator dataCheckValidator) {
        this.fileService = fileService;
        this.patternTableRepo = patternTableRepo;
        this.columnExporterRepo = columnExporterRepo;
        this.fileStatusCreator = fileStatusCreator;
        this.dataCheckValidator = dataCheckValidator;
    }

    @Override
    public List<FileStatusModel> checkDates(File file, Long id) throws Exception {
        List<FileStatusModel> fileStatusModels = generateFileStatusModels(file, id);
        file.delete();
        return fileStatusModels;
    }

    @Override
    public FileStatusModel checkData(File file, Long id) throws Exception {
        FileStatusModel fileStatusModel = generateFileStatusModel(file, id);
        file.delete();
        return fileStatusModel;
    }

    private List<FileStatusModel> generateFileStatusModels(File file, Long id) throws Exception {
        if (dataCheckValidator.checkByPatternId(file, id)) {
            return getFileStatusModels(file, id);
        } else {
            return null;
        }
    }

    private List<FileStatusModel> getFileStatusModels(File file, Long id) throws Exception {
        List<PatternTable> patternTables = patternTableRepo
                .findAllByPatternIdAndIsArchiveAndIsActive(id, false, true);

        List<FileStatusModel> fileStatusModels = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {
            fileStatusModels.add(generateFileStatusModel(file, patternTable.getId()));
        }

        return fileStatusModels;
    }

    private FileStatusModel generateFileStatusModel(File file, Long id) throws Exception {
        if (dataCheckValidator.checkByPatternTableId(file, id)) {
            return getFileStatusModel(file, id);
        } else {
            return null;
        }
    }

    private FileStatusModel getFileStatusModel(File file, Long id) throws Exception {
        PatternTable patternTable = patternTableRepo.findById((long) id);
        File targetFile = fileService.getFile(file, patternTable.getNameFile());

        return createFileStatusModel(targetFile, patternTable);
    }

    private FileStatusModel createFileStatusModel(File targetFile, PatternTable patternTable) throws Exception {
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

    private FileStatusModel createFileStatusModelOtherStatus(File targetFile, PatternTable patternTable) throws Exception {
        int fileColumnSize = getFileCountSize(targetFile);
        int tableColumnSize = columnExporterRepo.exportDataModel(patternTable.getNameTable()).size();

        return fileStatusCreator.getFileStatusModel(tableColumnSize, fileColumnSize,
                patternTable.getNameFile(), patternTable.getNameTable());
    }

    private int getFileCountSize(File targetFile) throws Exception {
        FileParser fileParser = FileParserFactory.getParser(FilenameUtils.getExtension(targetFile.getName()));
        Reader reader = fileParser.createReader(targetFile);

        return getNamesColumn(reader).size();
    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        List<String> nameColumns = reader.readNext();
        return ColumnCreator.createColumns(nameColumns);
    }
}
