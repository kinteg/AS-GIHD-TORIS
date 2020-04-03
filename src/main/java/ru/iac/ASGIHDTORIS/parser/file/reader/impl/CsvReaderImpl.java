package ru.iac.ASGIHDTORIS.parser.file.reader.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
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
        String[] strings = reader.readNext();

        return strings == null ? Collections.emptyList()
                : strings.length == 0
                || strings[0].trim().equals("")
                ? readNext()
                : Arrays.asList(strings);
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

}