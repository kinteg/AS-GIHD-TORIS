package ru.iac.ASGIHDTORIS.api.parser.reader;

import java.util.List;

public interface FileReader extends AutoCloseable{

    List<List<String>> getAll();

    List<String> getNextRecord();

    List<String> getFirst();

    @Override
    void close() throws Exception;
}
