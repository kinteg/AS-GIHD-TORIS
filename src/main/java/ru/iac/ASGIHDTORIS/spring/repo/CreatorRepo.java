package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;

@Repository
public interface CreatorRepo extends AutoCloseable {

    TableModelStatus createTable(TableModel tableModel);

}
