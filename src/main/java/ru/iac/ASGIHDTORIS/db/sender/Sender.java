package ru.iac.ASGIHDTORIS.db.sender;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.io.File;

@Repository
public interface Sender extends AutoCloseable {

    boolean insert(File file, TableModel tableModel);

}
