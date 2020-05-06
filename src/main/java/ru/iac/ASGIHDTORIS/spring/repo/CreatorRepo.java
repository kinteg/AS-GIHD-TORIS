package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

@Repository
public interface CreatorRepo extends AutoCloseable {

    TableModelStatus createTable(TableModel tableModel);

}
