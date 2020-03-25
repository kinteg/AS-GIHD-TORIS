package ru.iac.ASGIHDTORIS.api.parser;

import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;

import java.io.File;
import java.util.List;

public interface Parser {

    JSONObject getFromFile(File file, long limit, List<TableModel> tableModels) throws Exception;

    JSONObject getFormFiles(List<File> files, long limit, List<TableModel> tableModels) throws Exception;

}
