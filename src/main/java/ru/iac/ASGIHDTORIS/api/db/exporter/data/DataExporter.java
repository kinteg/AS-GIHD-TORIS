package ru.iac.ASGIHDTORIS.api.db.exporter.data;


import net.minidev.json.JSONObject;

public interface DataExporter {

    JSONObject exportData(String tableName, long limit) ;

}
