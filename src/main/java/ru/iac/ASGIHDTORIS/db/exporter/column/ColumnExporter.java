package ru.iac.ASGIHDTORIS.db.exporter.column;

import ru.iac.ASGIHDTORIS.common.model.data.DataModel;

import java.util.List;

public interface ColumnExporter extends AutoCloseable {

    List<DataModel> exportDataModel(String tableName);

}
