package ru.iac.ASGIHDTORIS.service;

import com.jayway.jsonpath.JsonPath;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.DataModel;
import ru.iac.ASGIHDTORIS.api.db.creator.Creator;
import ru.iac.ASGIHDTORIS.api.db.creator.DbPostgreSQLCreator;
import ru.iac.ASGIHDTORIS.api.db.loader.Loader;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.factory.loader.LoaderFactory;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DbService implements DBService {

    private final String QUERY = "$.content.columnTable[${i}]${key}";

    private final String ITERATOR_REGEX = "\\$\\{i\\}";
    private final String KEY_REGEX = "\\$\\{key\\}";

    private final DataSource dataSource;

    public DbService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String parseIntoBd(MultipartFile multipartFile, String tableInfo) throws IOException, SQLException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        String nameFile = JsonPath.read(tableInfo, "$.content.nameFile");
        String nameTable = JsonPath.read(tableInfo, "$.content.nameTable");

        ArchiveParser parser = ArchiveFactory.getParser(file.getName());

        File targetFile = parser.findByFileName(file, nameFile);

        List<DataModel> models = getModels(tableInfo);

        Creator creator = new DbPostgreSQLCreator(dataSource.getConnection());
        creator.createTable(nameTable, models);

        Loader loader = LoaderFactory.getParser(targetFile.getName(), dataSource.getConnection());
        boolean result = loader.insert(targetFile, nameTable, models);

        return result ? "ok" : "error";

    }

    private List<DataModel> getModels(String tableInfo) {
        List<DataModel> models = new ArrayList<>();

        String columnTable = QUERY.replaceFirst(ITERATOR_REGEX, "");
        columnTable = columnTable.replaceFirst(KEY_REGEX, "");
        List<String> columns = JsonPath.read(tableInfo, columnTable);

        String modelQuery;
        String key;
        String type;
        boolean primary;

        for (int i = 0; i < columns.size(); i++) {
            modelQuery = QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(i));

            key = modelQuery.replaceFirst(KEY_REGEX, ".key");
            type = modelQuery.replaceFirst(KEY_REGEX, ".type");
            primary = Boolean.parseBoolean(modelQuery.replaceFirst(KEY_REGEX, ".primary"));

            DataModel model = new DataModel(key, type, primary);

            models.add(model);

        }

        return models;
    }

}
