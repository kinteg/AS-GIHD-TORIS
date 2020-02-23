package ru.iac.ASGIHDTORIS.service.sender;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.model.DataModel;
import ru.iac.ASGIHDTORIS.api.db.sender.DataSender;
import ru.iac.ASGIHDTORIS.api.db.sender.FileSender;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DbSenderService implements DbService {

    private final String QUERY = "$.content.columnTable[${i}]${key}";

    private final String ITERATOR_REGEX = "\\$\\{i\\}";
    private final String KEY_REGEX = "\\$\\{key\\}";

    private final DataSource dataSource;

    public DbSenderService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String sendData(MultipartFile multipartFile, String tableInfo) throws IOException, SQLException {
        return send(multipartFile, tableInfo) ? "ok" : "error";
    }

    private boolean send(MultipartFile multipartFile, String tableInfo) throws IOException, SQLException {
        String nameFile = JsonPath.read(tableInfo, "$.content.nameFile");
        String nameTable = JsonPath.read(tableInfo, "$.content.nameTable");

        File file = parseFile(multipartFile, nameFile);

        List<DataModel> models = getModels(tableInfo);

        DataSender sender = new FileSender(file, dataSource.getConnection());

        return sender.send(models, nameTable);
    }

    private File parseFile(MultipartFile multipartFile, String nameFile) throws IOException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        file.deleteOnExit();

        if (ArchiveFactory.isArchive(file.getName())) {
            ArchiveParser parser = ArchiveFactory.getParser(file.getName());
            file = parser.findByFileName(file, nameFile);
        }

        file.delete();

        return file;
    }

    private List<DataModel> getModels(String tableInfo) {
        List<DataModel> models = new ArrayList<>();
        List<String> columns = JsonPath.read(tableInfo, "$.content.columnTable");

        String modelQuery;
        String name;
        String type;
        boolean primary;

        for (int i = 0; i < columns.size(); i++) {
            modelQuery = QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(i));
            name = JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, ".name"));
            type = JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, ".type"));
            primary = JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, ".primary"));

            DataModel model = new DataModel(name, type, primary);

            models.add(model);

        }

        return models;
    }

}
