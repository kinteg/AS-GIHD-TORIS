package ru.iac.ASGIHDTORIS.lib.lib.db.exporter;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;

import java.io.Closeable;
import java.util.List;

@Repository
public interface ColumnExporterRepo extends Closeable {

    List<DataModel> exportDataModel(String tableName);

}
