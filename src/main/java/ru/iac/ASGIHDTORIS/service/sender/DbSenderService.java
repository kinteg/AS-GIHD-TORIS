package ru.iac.ASGIHDTORIS.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.service.validator.DbValidService;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
    public String sendData(MultipartFile multipartFile, String nameFile, String nameTable, long sourceId) throws IOException {
        return dbValidService.isValid(multipartFile, sourceId) &&
                send(multipartFile, nameFile, nameTable) ? "ok" : "error";
    }

    private boolean send(MultipartFile multipartFile, String nameFile, String nameTable) throws IOException {

        File file = parseFile(multipartFile, nameFile);
        log.info("fg");
        List<DataModel> models = columnExporter.exportDataModel(nameTable);
        log.info(models.toString());
        boolean result = dataSender.send(file, models, nameTable);
        file.delete();

        return result;
    }


    private File parseFile(MultipartFile multipartFile, String nameFile) throws IOException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        file.deleteOnExit();

        if (ArchiveFactory.isArchive(file.getName())) {
            ArchiveParser parser = ArchiveFactory.getParser(file.getName());
            file = parser.findByFileName(file, nameFile);
        }

        return file;
    }

}
