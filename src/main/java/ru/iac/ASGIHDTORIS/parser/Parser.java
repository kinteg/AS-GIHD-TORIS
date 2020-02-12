package ru.iac.ASGIHDTORIS.parser;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;

import java.io.File;
import java.io.IOException;

public interface Parser {

    JSONObject getJSON(File file) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException;

}
