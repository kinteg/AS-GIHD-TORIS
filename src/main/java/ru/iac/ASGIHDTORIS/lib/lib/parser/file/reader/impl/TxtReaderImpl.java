package ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.impl;

import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TxtReaderImpl implements Reader {

    private final BufferedReader bufferedReader;
    private final String spliterator;

    public TxtReaderImpl(BufferedReader bufferedReader) {
        this(bufferedReader, ">");
    }

    public TxtReaderImpl(BufferedReader bufferedReader, String spliterator) {
        this.bufferedReader = bufferedReader;
        this.spliterator = spliterator;
    }

    @Override
    public String readLine() {
        return String.join(spliterator, readNext());
    }

    @Override
    public List<String> readNext() {
        return getRow();
    }

    private List<String> getRow() {
        try {
            String row = bufferedReader.readLine();

            return stringToList(row);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private List<String> stringToList(String row) {
        if (row == null) {
            return Collections.emptyList();
        } else if (row.trim().equals("")) {
            return readNext();
        } else {
            return Arrays.asList(row.split(spliterator));
        }
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}
