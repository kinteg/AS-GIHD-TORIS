package ru.iac.ASGIHDTORIS.api.db.model.table;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.List;

public interface TableModelCreator {

    List<TableModel> getTableModel();

    void setTableModel(String fileName, String tableName, List<DataModel> model);

    void setTableModel(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList);

}
