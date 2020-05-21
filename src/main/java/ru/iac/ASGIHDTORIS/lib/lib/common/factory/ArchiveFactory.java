package ru.iac.ASGIHDTORIS.lib.lib.common.factory;

import ru.iac.ASGIHDTORIS.lib.lib.common.target.archive.ArchiveExtension;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveParser;

public interface ArchiveFactory {

    ArchiveParser getArchiveParser(ArchiveExtension extension);

}
