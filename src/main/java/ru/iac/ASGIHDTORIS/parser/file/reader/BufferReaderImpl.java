package ru.iac.ASGIHDTORIS.parser.file.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BufferReaderImpl implements Reader {

    private final BufferedReader bufferedReader;
    private final String spliterator;

    public BufferReaderImpl(BufferedReader bufferedReader) {
        this(bufferedReader, ">");
    }

    public BufferReaderImpl(BufferedReader bufferedReader, String spliterator) {
        this.bufferedReader = bufferedReader;
        this.spliterator = spliterator;
    }

    @Override
    public String readLine() throws IOException {
        return bufferedReader.readLine();
    }

    @Override
    public List<String> readNext() throws IOException {
        String str = readLine();

        return str == null
                ? Collections.emptyList()
                : str.trim().equals("")
                ? readNext()
                : Arrays.asList(str.split(spliterator));
    }

    @Override
    public void close() throws Exception {
        bufferedReader.close();
    }
}
