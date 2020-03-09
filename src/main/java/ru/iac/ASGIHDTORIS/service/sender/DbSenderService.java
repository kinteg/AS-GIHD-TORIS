package ru.iac.ASGIHDTORIS.service.sender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.service.parser.json.JsonParser;
import ru.iac.ASGIHDTORIS.service.validator.DbValidService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class DbSenderService implements DbService {

    private final DataSender dataSender;
    private final JsonParser jsonParser;
    private final DbValidService dbValidService;
    private final PostgreSqlColumnExporter postgreSqlColumnExporter;

    public DbSenderService(DataSender dataSender, JsonParser jsonParser, DbValidService dbValidService, PostgreSqlColumnExporter postgreSqlColumnExporter) {
        this.dataSender = dataSender;
        this.jsonParser = jsonParser;
        this.dbValidService = dbValidService;
        this.postgreSqlColumnExporter = postgreSqlColumnExporter;
    }

    @Override
    public String sendData(MultipartFile multipartFile, String tableInfo, long sourceId) throws IOException {
        return dbValidService.isValid(multipartFile, sourceId) &&
                send(multipartFile, tableInfo) ? "ok" : "error";
    }

    private boolean send(MultipartFile multipartFile, String tableInfo) throws IOException {
        String nameFile = jsonParser.getFileName(tableInfo);
        String nameTable = jsonParser.getTableName(tableInfo);

        File file = parseFile(multipartFile, nameFile);

        List<DataModel> models = postgreSqlColumnExporter.exportDataModel(nameTable);

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
