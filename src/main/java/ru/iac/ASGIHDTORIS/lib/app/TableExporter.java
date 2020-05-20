package ru.iac.ASGIHDTORIS.lib.app;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;

import java.util.List;

public interface TableExporter {

    List<String> getTableNames();

    FullTableModel getTable(String name);

}
