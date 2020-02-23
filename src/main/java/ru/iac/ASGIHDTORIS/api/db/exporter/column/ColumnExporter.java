package ru.iac.ASGIHDTORIS.api.db.exporter.column;

import ru.iac.ASGIHDTORIS.api.db.DataModel;

import java.sql.SQLException;
import java.util.List;

public interface ColumnExporter {

    List<DataModel> exportDataModel(String tableName) throws SQLException;

}
