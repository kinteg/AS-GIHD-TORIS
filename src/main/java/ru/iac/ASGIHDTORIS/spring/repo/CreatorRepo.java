package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

@Repository
public interface CreatorRepo extends AutoCloseable {

    boolean createTable(TableModel tableModel);

}
