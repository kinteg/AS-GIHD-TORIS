package ru.iac.ASGIHDTORIS.service.parser.file;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.TableModel;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ParserFileService implements ParserService {

    private final Parser parserApi;
    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final ColumnExporter columnExporter;

    public ParserFileService(Parser parserApi, PatternRepo patternRepo, PatternTableRepo patternTableRepo, DataSource dataSource, ColumnExporter columnExporter) {
        this.parserApi = parserApi;
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.columnExporter = columnExporter;
    }

    @Override
    public String getWithParser(MultipartFile multipartFile, long limit, long sourceId) throws IOException, CsvValidationException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        List<TableModel> tableModels = tableModels(sourceId);
        JSONObject fromFile = parserApi.getFromFile(file, limit, tableModels);

        return fromFile.toJSONString();
    }

    private List<TableModel> tableModels(long sourceId) {
        Pattern pattern = patternRepo.findTopBySourceId(sourceId);

        List<PatternTable> patternTables = patternTableRepo.findByPatternId(pattern.getId());
        List<TableModel> tableModels = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            tableModels.add(createTableModel(patternTable));
        }

        return tableModels;
    }

    private TableModel createTableModel(PatternTable patternTable) {
        List<DataModel> dataModels = columnExporter.exportDataModel(patternTable.getNameTable());
        String tableName = patternTable.getNameTable();
        String fileName = patternTable.getNameFile();

        return new TableModel(fileName, tableName, dataModels);
    }

}
