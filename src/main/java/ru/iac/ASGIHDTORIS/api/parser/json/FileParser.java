package ru.iac.ASGIHDTORIS.api.parser.json;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileParser {

    JSONObject getJSON(File file) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit, List<DataModel> models) throws IOException, CsvValidationException;

    JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws IOException, CsvValidationException;

}
