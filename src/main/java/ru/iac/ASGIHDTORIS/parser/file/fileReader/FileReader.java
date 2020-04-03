package ru.iac.ASGIHDTORIS.parser.file.fileReader;

import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

public interface FileReader {

    FullTableModel createTableModel(TableModel tableModel, long limit) throws Exception;

}
