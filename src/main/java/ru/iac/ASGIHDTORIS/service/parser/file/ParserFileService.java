package ru.iac.ASGIHDTORIS.service.parser.file;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class ParserFileService implements ParserService {

    private final Parser parserApi;
    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final ColumnExporter columnExporter;
    private final TableModelCreator tableModelCreator;

    public ParserFileService(Parser parserApi, PatternRepo patternRepo, PatternTableRepo patternTableRepo, ColumnExporter columnExporter, TableModelCreator tableModelCreator) {
        this.parserApi = parserApi;
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.columnExporter = columnExporter;
        this.tableModelCreator = tableModelCreator;
    }

    @Override
    public String getWithParser(MultipartFile multipartFile, long limit, long sourceId) throws IOException, CsvValidationException {
        File file = FileConverter.multipartIntoFile(multipartFile);

        List<TableModel> tableModels = createTableModels(sourceId);

        if (tableModels == null) {
            return "";
        }

        JSONObject fromFile = parserApi.getFromFile(file, limit, tableModels);

        file.delete();

        return fromFile.toJSONString();
    }

    private List<TableModel> createTableModels(long sourceId) {
        Pattern pattern = patternRepo.findTopBySourceIdOrderByDateCreationDesc(sourceId);
        List<PatternTable> patternTables;

        if (pattern != null) {
            patternTables = patternTableRepo.findByPatternId(pattern.getId());
        } else {
            patternTables = Collections.emptyList();
        }

        List<List<DataModel>> modelList = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            modelList.add(columnExporter.exportDataModel(patternTable.getNameTable()));
            tableNames.add(patternTable.getNameTable());
            fileNames.add(patternTable.getNameFile());
        }

        tableModelCreator.setTableModel(fileNames, tableNames, modelList);

        return tableModelCreator.getTableModel();
    }

}
