package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {

    JSONObject getJSON(File file) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit, List<String> nameColumn) throws IOException, CsvValidationException;

}
