package ru.iac.ASGIHDTORIS.api.db.exporter.column;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.List;

public interface ColumnExporter {

    List<DataModel> exportDataModel(String tableName);

}
