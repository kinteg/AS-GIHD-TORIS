package ru.iac.ASGIHDTORIS.api.factory.archive;

import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.archive.zip.ZipParser;

public class ArchiveFactory {

    private ArchiveFactory() {}

    public static boolean isArchive(String filename) {
        TargetFiles targetFiles = new TargetFiles();
        return targetFiles.isArchive(filename);
    }

    public static ArchiveParser getParser(String fileName) {
        return changeParser(FilenameUtils.getExtension(fileName));
    }

    private static ArchiveParser changeParser(String extension) {

        switch (extension) {
            case "zip":
                return new ZipParser();
            case "rar" :

            default:
                return null;
        }

    }

}
