package ru.iac.ASGIHDTORIS.common.model.table;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

import java.util.List;

public interface TableModelCreator {

    List<TableModel> getTableModel();

    void setTableModel(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList);


}
