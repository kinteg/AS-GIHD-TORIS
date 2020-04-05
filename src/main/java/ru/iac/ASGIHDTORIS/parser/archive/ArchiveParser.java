package ru.iac.ASGIHDTORIS.parser.archive;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ArchiveParser extends AutoCloseable {

    File getFile(File zip, String name) throws IOException;

    List<File> getFiles(File file) throws IOException;

}
