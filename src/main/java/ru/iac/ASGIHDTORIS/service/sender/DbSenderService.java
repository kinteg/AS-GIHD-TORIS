package ru.iac.ASGIHDTORIS.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.service.validator.DbValidService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DbSenderService implements DbService {

    private final DataSender dataSender;
    private final DbValidService dbValidService;
    private final ColumnExporter columnExporter;

    public DbSenderService(DataSender dataSender, DbValidService dbValidService, ColumnExporter columnExporter) {
        this.dataSender = dataSender;
        this.dbValidService = dbValidService;
        this.columnExporter = columnExporter;
    }

    @Override
    public String sendData(MultipartFile multipartFile, List<TableModel> tableInfo, long sourceId) throws IOException {
        return dbValidService.isValid(multipartFile, sourceId) &&
                send(multipartFile, tableInfo) ? "ok" : "error";
    }

    private boolean send(MultipartFile multipartFile, List<TableModel> tableInfo) throws IOException {
        boolean result = false;

        List<File> files = parseFile(multipartFile);
        log.info(files.size() + "");

        for (TableModel tableModel : tableInfo) {
            String tableName = tableModel.getTableName();
            File file = getFile(files, tableModel.getFilename());

            List<DataModel> models = columnExporter.exportDataModel(tableName);
            log.info(models.size() + " send");
            result = dataSender.send(file, models, tableName);
            file.delete();
        }

        return result;
    }

    private File getFile(List<File> files, String fileName) {
        int i = files.stream().map(File::getName).collect(Collectors.toList()).indexOf(fileName);

        return files.get(i);
    }


    private List<File> parseFile(MultipartFile multipartFile) throws IOException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        file.deleteOnExit();

        List<File> files;

        if (ArchiveFactory.isArchive(file.getName())) {
            ArchiveParser parser = ArchiveFactory.getParser(file.getName());
            files = parser.getFiles(file);
        } else {
            files = Collections.singletonList(file);
        }

        return files;
    }

}
