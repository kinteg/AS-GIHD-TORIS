package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Parser {

    JSONObject getFromFile(File file, long limit) throws Exception;

    JSONObject getFromFile(File file, long limit, List<TableModel> tableModels) throws Exception;

    JSONObject getFormFiles(List<File> files, long limit, List<TableModel> tableModels) throws Exception;

}
