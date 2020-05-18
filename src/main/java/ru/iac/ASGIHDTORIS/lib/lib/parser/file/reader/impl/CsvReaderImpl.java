package ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.impl;

import com.opencsv.CSVReader;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CsvReaderImpl implements Reader {

    private final CSVReader csvReader;

    public CsvReaderImpl(CSVReader csvReader) {
        this.csvReader = csvReader;
    }

    @Override
    public String readLine() {
        return String.join(",", readNext());
    }

    @Override
    public List<String> readNext() {
        return getColumns();
    }

    private List<String> getColumns() {
        try {
            String[] columns = csvReader.readNext();

            return columnsToList(columns);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    private List<String> columnsToList(String[] columns) {
        if (columns == null) {
            return Collections.emptyList();

        } else if (columns.length == 0 || columns[0].trim().equals("")) {
            return readNext();

        } else {
            return Arrays.asList(columns);
        }
    }

    @Override
    public void close() throws Exception {
        csvReader.close();
    }
}
