package ru.iac.ASGIHDTORIS.common.factory;

import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.parser.archive.SevenZParser;
import ru.iac.ASGIHDTORIS.parser.archive.TarParser;
import ru.iac.ASGIHDTORIS.parser.archive.ZipParser;

public final class ArchiveFactory {

    private static final String ZIP = "zip";
    private static final String SEVEN_Z_PARSER = "7z";
    private static final String TAR_PARSER = "tar";

    public static ArchiveParser getParser(String extension) {

        switch (extension.toLowerCase()) {
            case ZIP:
                return new ZipParser();
            case SEVEN_Z_PARSER :
                return new SevenZParser();
            case TAR_PARSER :
                return new TarParser();
            default:
                return null;
        }

    }

}
