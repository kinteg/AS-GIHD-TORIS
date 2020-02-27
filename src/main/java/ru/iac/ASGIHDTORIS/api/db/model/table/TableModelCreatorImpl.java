package ru.iac.ASGIHDTORIS.api.db.model.table;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TableModelCreatorImpl implements TableModelCreator {

    private List<TableModel> models;

    @Override
    public List<TableModel> getTableModel() {
        return models;
    }

    @Override
    public void setTableModel(String fileName, String tableName, List<DataModel> models) {
        this.models = Collections.singletonList(createTableModel(fileName, tableName, models));
    }

    @Override
    public void setTableModel(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList) {
        this.models = createTableModels(fileNames, tableNames, modelList);
    }

    private List<TableModel> createTableModels(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList) {
        List<TableModel> tableModel = new ArrayList<>();

        for (int i = 0; i < fileNames.size() && i < tableNames.size() && i < modelList.size(); i++) {
            tableModel.add(createTableModel(fileNames.get(i), tableNames.get(i), modelList.get(i)));
        }

        return tableModel;
    }

    private TableModel createTableModel(String fileName, String tableName, List<DataModel> models) {
        return new TableModel(fileName, tableName, models);
    }

}
