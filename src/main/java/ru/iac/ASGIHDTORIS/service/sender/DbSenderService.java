package ru.iac.ASGIHDTORIS.service.sender;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.service.parser.json.JsonParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DbSenderService implements DbService {

    private final DataSender dataSender;
    private final JsonParser jsonParser;

    public DbSenderService(DataSender dataSender, JsonParser jsonParser) {
        this.dataSender = dataSender;
        this.jsonParser = jsonParser;
    }

    @Override
    public String sendData(MultipartFile multipartFile, String tableInfo) throws IOException {
        return send(multipartFile, tableInfo) ? "ok" : "error";
    }

    private boolean send(MultipartFile multipartFile, String tableInfo) throws IOException {
        String nameFile = jsonParser.getFileName(tableInfo);
        String nameTable = jsonParser.getTableName(tableInfo);

        File file = parseFile(multipartFile, nameFile);

        List<DataModel> models = jsonParser.getModels(tableInfo);

        return dataSender.send(file, models, nameTable);
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
