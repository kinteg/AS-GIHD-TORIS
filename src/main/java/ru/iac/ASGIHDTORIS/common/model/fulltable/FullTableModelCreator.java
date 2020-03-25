package ru.iac.ASGIHDTORIS.common.model.fulltable;

import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.util.List;

public interface FullTableModelCreator {

    List<FullTableModel> getTableModel();

    void setFullTableModel(TableModel tableModel, List<String> values);

    void setFullTableModel(List<TableModel> tableModels, List<List<String>> allValues);

}
