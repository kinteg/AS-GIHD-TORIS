package ru.iac.ASGIHDTORIS.api.factory.archive;

import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.archive.zip.ZipParser;
import ru.iac.ASGIHDTORIS.api.parser.archive.zip7.SevenZParser;

public final class ArchiveFactory {

    private static final String ZIP = "zip";
    private static final String SEVEN_Z_PARSER = "7z";

    private ArchiveFactory() {}

    public static boolean isArchive(String filename) {
        TargetFiles targetFiles = new TargetFiles();
        return targetFiles.isArchive(filename);
    }

    public static ArchiveParser getParser(String fileName) {
        return changeParser(FilenameUtils.getExtension(fileName));
    }

    private static ArchiveParser changeParser(String extension) {

        switch (extension.toLowerCase()) {
            case ZIP:
                return new ZipParser();
            case SEVEN_Z_PARSER :
                return new SevenZParser();
            default:
                return null;
        }

    }

}
