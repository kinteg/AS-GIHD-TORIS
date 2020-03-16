package ru.iac.ASGIHDTORIS.api.parser.json.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CsvReaderImpl implements Reader {

    private final CSVReader reader;

    public CsvReaderImpl(CSVReader reader) {
        this.reader = reader;
    }

    @Override
    public String readLine() throws Exception {
        return readNext().toString();
    }

    @Override
    public List<String> readNext() throws IOException, CsvValidationException {
        return Arrays.asList(reader.readNext());
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
