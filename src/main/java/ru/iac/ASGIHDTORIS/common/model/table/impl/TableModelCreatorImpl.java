package ru.iac.ASGIHDTORIS.common.model.table.impl;

import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

import java.util.ArrayList;
import java.util.List;

public class TableModelCreatorImpl implements TableModelCreator {

    private List<TableModel> models;

    @Override
    public List<TableModel> getTableModel() {
        return models;
    }

    @Override
    public void setTableModel(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList) {
        this.models = createTableModels(fileNames, tableNames, modelList);
    }

    private List<TableModel> createTableModels(List<String> fileNames, List<String> tableNames, List<List<DataModel>> modelList) {
        List<TableModel> fullTableModel = new ArrayList<>();

        for (int i = 0; i < fileNames.size() && i < tableNames.size() && i < modelList.size(); i++) {
            fullTableModel.add(createTableModel(fileNames.get(i), tableNames.get(i), modelList.get(i)));
        }

        return fullTableModel;
    }

    private TableModel createTableModel(String fileName, String tableName, List<DataModel> models) {
        return new TableModel(fileName, tableName, models);
    }

}
