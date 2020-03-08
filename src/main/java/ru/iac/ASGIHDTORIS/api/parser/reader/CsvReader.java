package ru.iac.ASGIHDTORIS.api.parser.reader;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader implements ru.iac.ASGIHDTORIS.api.parser.reader.FileReader {

    private CSVReader reader;
    private final List<List<String>> allList;
    private final List<String> firstRecord;
    private int nextRecord = -1;

    public CsvReader(File file) throws IOException, CsvException {
        this(new CSVReader(new FileReader(file)));
    }

    public CsvReader(CSVReader reader) throws IOException, CsvException {
        this.reader = reader;

        this.allList = reader.readAll()
                .stream()
                .map(Arrays::asList)
                .collect(Collectors.toList());
        firstRecord = allList.get(0);
    }

    @Override
    public List<List<String>> getAll() {
        return allList;
    }

    @Override
    public List<String> getNextRecord() {
        return ++nextRecord < allList.size() ?
                allList.get(nextRecord) :
                Collections.emptyList();
    }

    @Override
    public List<String> getFirst() {
        return firstRecord;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }


}
