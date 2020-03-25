package ru.iac.ASGIHDTORIS.parser.file.parser;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.FileReader;
import ru.iac.ASGIHDTORIS.parser.file.TableModelFixer;
import ru.iac.ASGIHDTORIS.parser.file.reader.BufferReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

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
        TableModelFixer tableModelFixer = new TableModelFixer();

        tableModel = tableModelFixer.fixTableModel(file, tableModel, reader);

        FileReader creator = new FileReader(reader);

        return creator.createTableModel(tableModel, limit);
    }

    private Reader createReader(File file) throws Exception {
        return new BufferReaderImpl(Files.newBufferedReader(Paths.get(file.getAbsolutePath()), Charset.forName("windows-1251")));
    }

}
