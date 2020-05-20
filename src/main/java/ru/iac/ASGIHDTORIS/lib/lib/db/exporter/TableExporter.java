package ru.iac.ASGIHDTORIS.lib.lib.db.exporter;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;

public interface TableExporter {

    FullTableModel tableExporter(String tableName, int limit);

}
