package ru.iac.ASGIHDTORIS.parser.file.parser.impl;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.fileReader.FileReader;
import ru.iac.ASGIHDTORIS.parser.file.fileReader.FileReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.fixer.TableModelFixer;
import ru.iac.ASGIHDTORIS.parser.file.fixer.TableModelFixerImpl;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.reader.impl.CsvReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.io.File;
import java.io.FileNotFoundException;

@Slf4j
public class CsvParser implements FileParser {

    @Override
    public FullTableModel getFullTable(File file, long limit) throws Exception {
        return getFullTable(file, limit, new TableModel());
    }

    @Override
    public FullTableModel getFullTable(File file, long limit, TableModel tableModel) throws Exception {
        return csvReader(file, limit, tableModel);
    }

    private FullTableModel csvReader(File file, long limit, TableModel tableModel) throws Exception {
        Reader reader = createReader(file);
        TableModelFixer tableModelFixerImpl = new TableModelFixerImpl();

        tableModel = tableModelFixerImpl.fixTableModel(file, tableModel, reader);

        FileReader creator = new FileReaderImpl(reader);

        return creator.createTableModel(tableModel, limit);
    }

    private Reader createReader(File file) throws FileNotFoundException {
        return new CsvReaderImpl(new CSVReader(new java.io.FileReader(file)));
    }

}
