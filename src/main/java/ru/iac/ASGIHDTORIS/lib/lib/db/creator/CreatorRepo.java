package ru.iac.ASGIHDTORIS.lib.lib.db.creator;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModelStatus;

public interface CreatorRepo extends AutoCloseable {

    TableModelStatus createTable(TableModel tableModel);

}
