package ru.iac.ASGIHDTORIS.parser.file.parser;

import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.io.File;
import java.io.IOException;

public interface FileParser {

    FullTableModel getFullTable(File file, long limit) throws Exception;

    FullTableModel getFullTable(File file, long limit, TableModel tableModel) throws Exception;

    Reader createReader(File file) throws Exception;
}
