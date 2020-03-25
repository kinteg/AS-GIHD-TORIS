package ru.iac.ASGIHDTORIS.parser.file.parser;

import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.io.File;

public interface FileParser {

    FullTableModel getFullTable(File file, long limit) throws Exception;

    FullTableModel getFullTable(File file, long limit, TableModel tableModel) throws Exception;

}
