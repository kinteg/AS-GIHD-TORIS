package ru.iac.ASGIHDTORIS.parser.zip;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ZipParser {

    List<File> getFiles(File file) throws IOException;

}
