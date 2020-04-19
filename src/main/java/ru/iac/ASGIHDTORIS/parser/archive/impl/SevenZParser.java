package ru.iac.ASGIHDTORIS.parser.archive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.ArchiveValidator;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.impl.ArchiveValidatorImpl;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.iac.ASGIHDTORIS.parser.archive.impl.ArchiveHelper.createFile;

@Slf4j
public class SevenZParser implements ArchiveParser {

    private byte[] buffer;
    private SevenZFile sevenZFile;
    private final ArchiveValidator archiveValidator;

    public SevenZParser() {
        buffer = new byte[1024];
        archiveValidator = new ArchiveValidatorImpl();
    }

    @Override
    public File getFile(File zip, String name) throws IOException {
        return unzipFile(zip, name);
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    @Override
    public void close() throws Exception {
        sevenZFile.close();
    }

    private File unzipFile(File zip, String name) throws IOException {
        sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (archiveValidator.isValidEntry(zipEntry.getName(), name)) {
                return createFile(zipEntry.getName(), sevenZFile);
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
                files.add(createFile(zipEntry.getName(), sevenZFile));
            }
        }

        return files;
    }
}