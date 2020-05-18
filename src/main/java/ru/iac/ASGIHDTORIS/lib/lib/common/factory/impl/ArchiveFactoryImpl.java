package ru.iac.ASGIHDTORIS.lib.lib.common.factory.impl;

import ru.iac.ASGIHDTORIS.lib.lib.common.factory.ArchiveFactory;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.archive.ArchiveExtension;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser.ArchiveParserImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser.SevenZParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser.TarParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser.ZipParser;

public class ArchiveFactoryImpl implements ArchiveFactory {

    @Override
    public ArchiveParser getArchiveParser(ArchiveExtension extension) {

        ArchiveParser archiveParser;

        switch (extension) {
            case TAR:
                archiveParser = new TarParser();
                break;

            case ZIP:
                archiveParser = new ZipParser();
                break;

            case SEVEN_Z:
                archiveParser = new SevenZParser();
                break;

            case NON_TARGET:
            default:
                archiveParser = new ArchiveParserImpl();
        }

        return archiveParser;
    }

}
