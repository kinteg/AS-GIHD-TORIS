package ru.iac.ASGIHDTORIS.api.db.exporter.parser;


import net.minidev.json.JSONObject;

import java.util.List;

public interface DbParser {

    JSONObject getFromDb(String tableName, long limit);

    JSONObject getFromDb(List<String> tableNames, long limit);

}
