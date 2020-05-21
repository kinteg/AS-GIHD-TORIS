package ru.iac.ASGIHDTORIS.lib.lib.parser.archive;

import java.io.File;

public interface ArchiveHelper<T extends ArchiveReader> {

    File createFile(String name, T zis);

}
