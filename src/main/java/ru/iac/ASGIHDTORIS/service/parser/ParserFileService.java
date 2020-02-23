package ru.iac.ASGIHDTORIS.service.parser;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.parser.DbDataParser;
import ru.iac.ASGIHDTORIS.api.db.exporter.parser.DbParser;
import ru.iac.ASGIHDTORIS.api.db.model.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.TableModel;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.PostgreSqlColumnExporter;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParserFileService implements ParserService {

    private final Parser parserApi;
    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final DataSource dataSource;

    public ParserFileService(Parser parserApi, PatternRepo patternRepo, PatternTableRepo patternTableRepo, DataSource dataSource) {
        this.parserApi = parserApi;
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.dataSource = dataSource;
    }

    @Override
    public String getWithParser(MultipartFile multipartFile, long limit, String sourceId) throws IOException, CsvValidationException, SQLException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        List<TableModel> tableModels = tableModels(sourceId);
        JSONObject fromFile = parserApi.getFromFile(file, limit, tableModels);

        return fromFile.toJSONString();
    }

    private List<TableModel> tableModels(String sourceId) throws SQLException {
        Pattern pattern = patternRepo.findTopBySourceId(Long.parseLong(sourceId));

        List<PatternTable> patternTables = patternTableRepo.findByPatternId(pattern.getId());
        List<TableModel> tableModels = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            tableModels.add(createTableModel(patternTable));
        }

        return tableModels;
    }

    private TableModel createTableModel(PatternTable patternTable) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ColumnExporter exporter = new PostgreSqlColumnExporter(connection);

            List<DataModel> dataModels = exporter.exportDataModel(patternTable.getNameTable());
            String tableName = patternTable.getNameTable();
            String fileName = patternTable.getNameFile();

            return new TableModel(fileName, tableName, dataModels);
        } catch (Exception ex) {
            return null;
        }

    }

}
