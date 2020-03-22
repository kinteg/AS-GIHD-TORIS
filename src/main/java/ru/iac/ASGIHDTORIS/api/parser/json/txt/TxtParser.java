package ru.iac.ASGIHDTORIS.api.parser.json.txt;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.parser.json.ColumnCreator;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;
import ru.iac.ASGIHDTORIS.api.parser.json.JsonCreator;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.BufferReaderImpl;
import ru.iac.ASGIHDTORIS.api.parser.json.reader.Reader;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class TxtParser implements FileParser {

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
        return txtReader(file, limit, models, tableName);
    }

    private JSONObject txtReader(File file, long limit, List<DataModel> models, String tableName) throws Exception {
        Reader reader = createReader(file);

        if (models.isEmpty()) {
            models = getNamesColumn(reader);
        }

        JsonCreator creator = new JsonCreator(reader);

        return creator.createJson(file.getName(), models, limit, tableName);
    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        String allNames = reader.readLine();
        List<String> nameColumns = fixList(allNames);

        return ColumnCreator.createColumns(nameColumns);
    }

    private List<String> fixList(String allNames) {
        List<String> nameColumns = new ArrayList<>(Arrays.asList(allNames.split(">")));

        int columns = StringUtils.countMatches(allNames, '>') + 1;

        if (nameColumns.size() < columns) {
            for (int i = nameColumns.size(); i < columns; i++) {
                nameColumns.add("");
            }
        }

        return nameColumns;
    }

    private Reader createReader(File file) throws Exception {
        return new BufferReaderImpl(Files.newBufferedReader(Paths.get(file.getName()), Charset.forName("windows-1251")));
    }

}
