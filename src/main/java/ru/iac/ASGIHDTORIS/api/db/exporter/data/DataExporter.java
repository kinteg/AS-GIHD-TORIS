package ru.iac.ASGIHDTORIS.api.db.exporter.data;

import java.util.List;

public interface DataExporter {

    List<String> exportData(String tableName, long limit) ;

}
