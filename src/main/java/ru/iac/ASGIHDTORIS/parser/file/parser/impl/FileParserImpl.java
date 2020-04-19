package ru.iac.ASGIHDTORIS.parser.file.parser.impl;

import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.parser.file.reader.impl.ReaderImpl;

import java.io.File;

public class FileParserImpl implements FileParser {
    @Override
    public FullTableModel getFullTable(File file, long limit) throws Exception {
        return getFullTable(file, limit, new TableModel());
    }

    @Override
    public FullTableModel getFullTable(File file, long limit, TableModel tableModel) throws Exception {
        return FullTableModel.emptyFullTableModel();
    }

    @Override
    public Reader createReader(File file) throws Exception {
        return new ReaderImpl();
    }
}
