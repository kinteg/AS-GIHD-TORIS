package ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveHelper;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveReader;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader.ArchiveHelperImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader.SevenZFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.validator.ArchiveValidator;
import ru.iac.ASGIHDTORIS.lib.lib.validator.impl.ArchiveValidatorImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SevenZParser implements ArchiveParser {

    private SevenZFile sevenZFile;
    private final ArchiveValidator archiveValidator;

    public SevenZParser() {
        archiveValidator = new ArchiveValidatorImpl();
    }

    @Override
    public File getFile(File zip, String name) {
        try {
            return unzipFile(zip, name);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public List<File> getFiles(File zip) {
        try {
            return unzipFiles(zip);
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private File unzipFile(File zip, String name) throws IOException {
        sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (archiveValidator.isValidEntry(zipEntry.getName(), name)) {
                return getFile(zipEntry.getName(), sevenZFile);
            }
        }

        return null;
    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (archiveValidator.isTargetFile(zipEntry.getName())) {
                files.add(getFile(zipEntry.getName(), sevenZFile));
            }
        }

        return files;
    }

    private File getFile(String name, SevenZFile zis) {
        ArchiveReader reader = new SevenZFileImpl(zis);
        ArchiveHelper<ArchiveReader> helper = new ArchiveHelperImpl<>();

        return helper.createFile(name, reader);
    }

    @Override
    public void close() throws Exception {
        sevenZFile.close();
    }
}