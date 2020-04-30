package ru.iac.ASGIHDTORIS.spring.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SenderRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.util.List;

@Service
@Slf4j
public class FileSenderServiceImpl implements FileSenderService {

    private final ColumnExporterRepo columnCreator;
    private final SenderRepo senderRepo;
    private final FileService fileService;

    public FileSenderServiceImpl(@Qualifier("postgreSqlColumnExporterRepo") ColumnExporterRepo columnCreator, SenderRepo senderRepo, FileService fileService) {
        this.columnCreator = columnCreator;
        this.senderRepo = senderRepo;
        this.fileService = fileService;
    }

    @Override
    public boolean sendFile(PatternTable patternTable, File file) {
        File targetFile = fileService.getFile(file, patternTable.getNameFile());
        if (targetFile == null) {
            return false;
        }
        List<DataModel> dataModels = columnCreator.exportDataModel(patternTable.getNameTable());

        TableModel tableModel = TableModel
                .builder()
                .models(dataModels)
                .tableName(patternTable.getNameTable())
                .build();

        boolean result = senderRepo.insert(targetFile, tableModel);
        targetFile.delete();
        return result;
    }

    @Override
    public boolean sendFiles(List<PatternTable> patternTables, File file) {
        for (PatternTable patternTable :
                patternTables) {

            sendFile(patternTable, file);
        }

        return true;
    }

}
