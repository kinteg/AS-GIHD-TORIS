package ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.parser;


import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveHelper;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveReader;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader.ArchiveHelperImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader.ZipInputStreamImpl;
import ru.iac.ASGIHDTORIS.lib.lib.validator.ArchiveValidator;
import ru.iac.ASGIHDTORIS.lib.lib.validator.impl.ArchiveValidatorImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipParser implements ArchiveParser {

    private ZipInputStream zis;
    private final ArchiveValidator archiveValidator;

    public ZipParser() {
        archiveValidator = new ArchiveValidatorImpl();
    }

    @Override
    public File getFile(File zip, String name) {
        try {
            return unzipFile(zip, name);
        } catch (Exception ex) {
            return null;
        }

    }

    @Override
    public List<File> getFiles(File zip) {
        try {
            return unzipFiles(zip);
        } catch (Exception ex) {
            return Collections.emptyList();
        }
    }

    private File unzipFile(File zip, String name) throws IOException {
        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;
        File file = null;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (archiveValidator.isValidEntry(zipEntry.getName(), name)) {
                file = getFile(zis, zipEntry.getName());
            }
        }

        return file;

    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (archiveValidator.isTargetFile(zipEntry.getName())) {
                files.add(getFile(zis, zipEntry.getName()));
            }
        }

        return files;

    }

    private File getFile(ZipInputStream zis, String name) {
        ArchiveReader reader = new ZipInputStreamImpl(zis);
        ArchiveHelper<ArchiveReader> helper = new ArchiveHelperImpl<>();

        return helper.createFile(name, reader);
    }

    @Override
    public void close() throws Exception {
        zis.closeEntry();
        zis.close();
    }

}