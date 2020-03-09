package ru.iac.ASGIHDTORIS.api.parser.json.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.Arrays;

public class CsvReaderImpl implements Reader {

    private final CSVReader reader;

    public CsvReaderImpl(CSVReader reader) {
        this.reader = reader;
    }

    @Override
    public String readLine() throws Exception {
        return Arrays.toString(readNext());
    }

    @Override
    public String[] readNext() throws IOException, CsvValidationException {
        return reader.readNext();
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
