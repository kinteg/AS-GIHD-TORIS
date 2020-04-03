package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;

import java.util.List;

@Repository
public interface ColumnExporterRepo extends AutoCloseable {

    List<DataModel> exportDataModel(String tableName);

}
