package ru.iac.ASGIHDTORIS.parser.archive.impl;

import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ArchiveParserImpl implements ArchiveParser {

    @Override
    public File getFile(File zip, String name) throws IOException {
        return null;
    }

    @Override
    public List<File> getFiles(File file) throws IOException {
        return Collections.emptyList();
    }

    @Override
    public void close() throws Exception {
    }

}
