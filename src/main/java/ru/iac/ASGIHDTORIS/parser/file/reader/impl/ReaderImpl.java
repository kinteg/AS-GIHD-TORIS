package ru.iac.ASGIHDTORIS.parser.file.reader.impl;

import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.util.Collections;
import java.util.List;

public class ReaderImpl implements Reader {
    @Override
    public String readLine() throws Exception {
        return "";
    }

    @Override
    public List<String> readNext() throws Exception {
        return Collections.emptyList();
    }

    @Override
    public void close() throws Exception {
    }

}
