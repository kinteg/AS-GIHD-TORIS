package ru.iac.ASGIHDTORIS.parser.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.parser.Parser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SimpleCsvParser implements Parser {

    private CSVReader reader;
    private final long CUSTOM_LIMIT = 20;
    private final long WITHOUT_LIMIT = -1;

    @Override
    public JSONObject getJSON(File file) throws IOException, CsvValidationException {
        return getJSON(file, CUSTOM_LIMIT);
    }

    @Override
    public JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException {
        return isNull(file) ? null : csvReader(file, limit);
    }

    private boolean isNull(File file) {
        return file == null;
    }

    private JSONObject csvReader(File file, long limit) throws IOException, CsvValidationException {
        reader = new CSVReader(new FileReader(file));

        String[] nameColumn = getNameColumn();

        return createJson(nameColumn, limit);
    }

    private String[] getNameColumn() throws IOException, CsvValidationException {
        return reader.readNext();
    }

    private JSONObject createJson(String[] nameColumn, long limit) throws IOException, CsvValidationException {
        JSONObject parsed = new JSONObject();
        JSONArray array;

        if (limit == WITHOUT_LIMIT) {
            array = getWithoutLimit(nameColumn);
        } else {
            array = getWithLimit(nameColumn, limit);
        }

        parsed.put("content", array);

        return parsed;
    }

    private JSONArray getWithoutLimit(String[] nameColumn) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        while ((nextRecord = reader.readNext()) != null) {
            array.add(getJsonObject(nameColumn, nextRecord));
        }

        return array;
    }

    private JSONArray getWithLimit(String[] nameColumn, long limit) throws IOException, CsvValidationException {
        JSONArray array = new JSONArray();
        String[] nextRecord;

        for (int i = 0; (nextRecord = reader.readNext()) != null || i < limit; i++) {
            array.add(getJsonObject(nameColumn, nextRecord));
        }

        return array;
    }

    private JSONObject getJsonObject(String[] nameColumn, String[] record) {
        JSONObject jsonObject = new JSONObject();

        for (int i = 0; i < nameColumn.length; i++) {
            jsonObject.put(nameColumn[i], record[i]);
        }

        return jsonObject;
    }
}
