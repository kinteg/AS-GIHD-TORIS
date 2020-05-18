package ru.iac.ASGIHDTORIS.lib.app;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModelStatus;

import java.io.File;
import java.util.List;

@Component
public interface SimpleTableCreator {

    List<TableModelStatus> createTable(File file);

    List<TableModelStatus> createTable(List<TableModel> tableModel);

    TableModelStatus createTable(TableModel tableModel);

}
