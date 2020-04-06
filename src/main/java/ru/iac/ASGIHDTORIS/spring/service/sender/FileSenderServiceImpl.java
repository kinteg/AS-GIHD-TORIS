package ru.iac.ASGIHDTORIS.spring.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.TargetFiles;
import ru.iac.ASGIHDTORIS.common.factory.ArchiveFactory;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SenderRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class FileSenderServiceImpl implements FileSenderService {

    private final ColumnExporterRepo columnCreator;
    private final SenderRepo senderRepo;

    public FileSenderServiceImpl(@Qualifier("postgreSqlColumnExporterRepo") ColumnExporterRepo columnCreator, SenderRepo senderRepo) {
        this.columnCreator = columnCreator;
        this.senderRepo = senderRepo;
    }

    @Override
    public boolean sendFile(PatternTable patternTable, File file) {
        File targetFile = getFile(file, patternTable.getNameFile());
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
        file.delete();
        return result;
    }

    @Override
    public boolean sendFiles(List<PatternTable> patternTables, File file) {
        for (PatternTable patternTable :
                patternTables) {
            File targetFile = getFile(file, patternTable.getNameFile());
            if (targetFile == null) {
                break;
            }
            List<DataModel> dataModels = columnCreator.exportDataModel(patternTable.getNameTable());

            TableModel tableModel = TableModel
                    .builder()
                    .models(dataModels)
                    .tableName(patternTable.getNameTable())
                    .build();

            senderRepo.insert(targetFile, tableModel);
            targetFile.delete();
        }

        file.delete();

        return true;
    }

    private File getFile(File file, String filename) {
        try {
            if (TargetFiles.isArchive(file.getName())) {
                return getFileWithArchive(file, filename);
            } else if (TargetFiles.isTargetFile(file.getName())) {
                return checkFile(file, filename);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    private File getFileWithArchive(File file, String filename) throws IOException {
        ArchiveParser parser = ArchiveFactory.getParser(FileNameUtils.getExtension(file.getName()));

        if (parser == null) {
            return null;
        }
        return parser.getFile(file, filename);
    }

    private File checkFile(File file, String filename) {
        if (file.getName().toLowerCase().equals(filename.toLowerCase())) {
            return file;
        }
        return null;
    }

}
