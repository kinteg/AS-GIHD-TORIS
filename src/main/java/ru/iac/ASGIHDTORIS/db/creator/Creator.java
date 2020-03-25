package ru.iac.ASGIHDTORIS.db.creator;

import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

public interface Creator extends AutoCloseable {

    boolean createTable(TableModel tableModel);

}
