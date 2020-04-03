package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.io.File;

@Repository
public interface SenderRepo extends AutoCloseable {

    boolean insert(File file, TableModel tableModel);

}
