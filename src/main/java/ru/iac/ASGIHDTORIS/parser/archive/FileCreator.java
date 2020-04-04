package ru.iac.ASGIHDTORIS.parser.archive;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.IOException;

public interface FileCreator {

    File getFile(String name, int read, byte[] buffer) throws IOException;

}
