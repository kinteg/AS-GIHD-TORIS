package ru.iac.ASGIHDTORIS.parser.archive.impl;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.ArchiveValidator;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.impl.ArchiveValidatorImpl;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static ru.iac.ASGIHDTORIS.parser.archive.impl.ArchiveHelper.createFile;

@Slf4j
public class ZipParser implements ArchiveParser {

    private ZipInputStream zis;
    private final ArchiveValidator archiveValidator;

    public ZipParser() {
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
        zis.closeEntry();
        zis.close();
    }

    private File unzipFile(File zip, String name) throws IOException {
        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (archiveValidator.isValidEntry(zipEntry.getName(), name)) {

                return createFile(zipEntry.getName(), zis);
            }
        }

        return null;
    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (archiveValidator.isTargetFile(zipEntry.getName())) {
                files.add(createFile(zipEntry.getName(), zis));
            }
        }

        return files;
    }
}