package ru.iac.ASGIHDTORIS.api;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ParserApi {

    JSONObject getFromFile(File file, long limit) throws IOException, CsvValidationException;

    JSONObject getFormFiles(List<File> files, long limit) throws IOException, CsvValidationException;

}
