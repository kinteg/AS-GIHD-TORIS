package ru.iac.ASGIHDTORIS.api.parser.json;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileParser {

    long CUSTOM_LIMIT = 20;

    JSONObject getJSON(File file) throws Exception;

    JSONObject getJSON(File file, long limit) throws Exception;

    JSONObject getJSON(File file, long limit, List<DataModel> models) throws Exception;

    JSONObject getJSON(File file, long limit, List<DataModel> models, String tableName) throws Exception;

}
