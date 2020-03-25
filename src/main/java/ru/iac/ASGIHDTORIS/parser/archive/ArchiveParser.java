package ru.iac.ASGIHDTORIS.parser.archive;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ArchiveParser {

    List<File> getFiles(File file) throws IOException;

}
