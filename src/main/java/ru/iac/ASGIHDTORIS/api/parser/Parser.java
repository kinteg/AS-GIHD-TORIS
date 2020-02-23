package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {

    JSONObject getFromFile(File file, long limit) throws IOException, CsvValidationException;

    JSONObject getFromFile(File file, long limit, List<DataModel> models) throws IOException, CsvValidationException;

    JSONObject getFromFile(File file, long limit, List<DataModel> models, String tableName) throws IOException, CsvValidationException;

//    JSONObject getFromFile(File file, String fileName) throws IOException, CsvValidationException;

    JSONObject getFormFiles(List<File> files, long limit, List<DataModel> models, String tableName) throws IOException, CsvValidationException;

}
