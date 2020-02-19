package ru.iac.ASGIHDTORIS.api.parser.zip;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ZipParser {

    List<File> getFiles(File file) throws IOException;

    File findByFileName(File file, String filename) throws IOException;

}
