package ru.iac.ASGIHDTORIS.lib.lib.common.factory;

import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.FileExtension;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.File;

public interface FileFactory {

    Reader getFileReader(FileExtension extension, File file);

}
