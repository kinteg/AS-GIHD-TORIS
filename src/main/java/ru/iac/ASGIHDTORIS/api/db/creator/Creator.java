package ru.iac.ASGIHDTORIS.api.db.creator;

import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.util.List;

public interface Creator {

    boolean createTable(String tableName, List<DataModel> models);

}
