package ru.iac.ASGIHDTORIS.api.parser.json.xlsx;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.ColumnCreator;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;
import ru.iac.ASGIHDTORIS.api.parser.json.JsonCreator;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.CsvReaderImpl;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

@Slf4j
public class XlsxParser implements FileParser {

    @Override
    public JSONObject getJSON(File file, long limit) throws Exception {
        return getJSON(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models) throws Exception {
        return getJSON(file, limit, models, FilenameUtils.getBaseName(file.getAbsolutePath()));
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws Exception {
        return xlsxReader(file, limit, models, tableName);
    }

    private JSONObject xlsxReader(File file, long limit, List<DataModel> models, String tableName) throws Exception {
        Reader reader = createReader(file);

        if (models.isEmpty()) {
            models = getNamesColumn(reader);
        }

        JsonCreator creator = new JsonCreator(reader);

        return creator.createJson(file.getName(), models, limit, tableName);
    }

    private Reader createReader(File file) throws FileNotFoundException {
        return new CsvReaderImpl(new CSVReader(new FileReader(file)));
    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        List<String> nameColumns = reader.readNext();
        log.info(nameColumns.toString());

        return ColumnCreator.createColumns(nameColumns);
    }

}