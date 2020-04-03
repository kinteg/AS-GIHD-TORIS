package ru.iac.ASGIHDTORIS.db.exporter.column;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;

import java.util.List;

@Repository
public interface ColumnExporter extends AutoCloseable {

    List<DataModel> exportDataModel(String tableName);

}
