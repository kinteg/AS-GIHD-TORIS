package ru.iac.ASGIHDTORIS.parser.csv.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
@Slf4j
public class CsvParserImpl implements ru.iac.ASGIHDTORIS.parser.csv.CsvParser {

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
                : csvReader(file, limit);
    }

    private boolean isNull(File file) {
        return file == null;
    }

    private JSONObject csvReader(File file, long limit) throws IOException, CsvValidationException {
        reader = new CSVReader(new FileReader(file));

        String[] nameColumn = getNameColumn();

        return createJson(file.getName(), nameColumn, limit);
    }

    private String[] getNameColumn() throws IOException, CsvValidationException {
        return reader.readNext();
    }

    private JSONObject createJson(String filename, String[] nameColumn, long limit) throws IOException, CsvValidationException {
        JSONObject parsed = new JSONObject();
        JSONArray array;

        if (limit == WITHOUT_LIMIT) {
            array = getWithoutLimit(nameColumn);
        } else {
            array = getWithLimit(nameColumn, limit);
        }

        parsed.put("nameTable", filename);
        parsed.put("table", array);

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
        for (int j = 0; (nextRecord = reader.readNext()) != null && j < limit; j++) {
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
