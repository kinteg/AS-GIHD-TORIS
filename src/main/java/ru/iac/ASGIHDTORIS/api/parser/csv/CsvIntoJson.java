package ru.iac.ASGIHDTORIS.api.parser.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class CsvIntoJson implements ru.iac.ASGIHDTORIS.api.parser.csv.CsvParser {

    private CSVReader reader;
    private final long CUSTOM_LIMIT = 20;
    private final long WITHOUT_LIMIT = -1;

    @Override
    public JSONObject getJSON(File file) throws IOException, CsvValidationException {
        return getJSON(file, CUSTOM_LIMIT);
    }

    @Override
    public JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException {
        return isNull(file)
                ? new JSONObject()
                : csvReader(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getJSON(File file, long limit, List<String> names) throws IOException, CsvValidationException {
        return csvReader(file, limit, names);
    }

    private boolean isNull(File file) {
        return file == null;
    }

    private JSONObject csvReader(File file, long limit, List<String> nameColumn) throws IOException, CsvValidationException {
        reader = new CSVReader(new FileReader(file));

        if (nameColumn.isEmpty()) {
            nameColumn = getNamesColumn();
        }

        return createJson(file.getName(), nameColumn, limit);
    }

    private List<String> getNamesColumn() throws IOException, CsvValidationException {
        return Arrays.asList(reader.readNext());
    }

    private JSONObject createJson(String filename, List<String> namesColumn, long limit) throws IOException, CsvValidationException {
        JSONObject parsed = new JSONObject();
        JSONArray array;

        if (limit == WITHOUT_LIMIT) {
            array = getWithoutLimit(namesColumn);
        } else {
            array = getWithLimit(namesColumn, limit);
        }

        parsed.put("nameTable", filename);
        parsed.put("table", array);

        return parsed;
    }

    private JSONArray getWithoutLimit(List<String> nameColumn) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        while ((nextRecord = reader.readNext()) != null) {
            array.add(getJsonObject(nameColumn, nextRecord));
        }

        return array;
    }

    private JSONArray getWithLimit(List<String> nameColumn, long limit) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        for (int j = 0; (nextRecord = reader.readNext()) != null && j < limit; j++) {
            array.add(getJsonObject(nameColumn, nextRecord));
        }

        return array;
    }

    private JSONObject getJsonObject(List<String> nameColumn, String[] record) {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < nameColumn.size() && i < record.length; i++) {
            jsonObject.put(nameColumn.get(i).trim(), record[i].trim());
        }

        return jsonObject;
    }
}
