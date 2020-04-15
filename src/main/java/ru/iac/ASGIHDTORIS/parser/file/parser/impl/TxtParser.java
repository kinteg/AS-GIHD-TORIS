package ru.iac.ASGIHDTORIS.parser.file.parser.impl;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.fileReader.FileReader;
import ru.iac.ASGIHDTORIS.parser.file.fileReader.impl.FileReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.fixer.TableModelFixer;
import ru.iac.ASGIHDTORIS.parser.file.fixer.impl.TableModelFixerImpl;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.parser.file.reader.impl.BufferReaderImpl;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class TxtParser implements FileParser {

    @Override
    public FullTableModel getFullTable(File file, long limit) throws Exception {
        return getFullTable(file, limit, new TableModel());
    }

    @Override
    public FullTableModel getFullTable(File file, long limit, TableModel tableModel) throws Exception {
        return txtReader(file, limit, tableModel);
    }

    private FullTableModel txtReader(File file, long limit, TableModel tableModel) throws Exception {
        Reader reader = createReader(file);
        TableModelFixer tableModelFixerImpl = new TableModelFixerImpl();

        tableModel = tableModelFixerImpl.fixTableModel(file, tableModel, reader);

        FileReader creator = new FileReaderImpl(reader);

        return creator.createTableModel(tableModel, limit);
    }

    @Override
    public Reader createReader(File file) throws Exception {
        return new BufferReaderImpl(Files.newBufferedReader(Paths.get(file.getAbsolutePath()), Charset.forName("windows-1251")));
    }

}
