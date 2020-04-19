package ru.iac.ASGIHDTORIS.parser.archive.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.ArchiveValidator;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.impl.ArchiveValidatorImpl;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.iac.ASGIHDTORIS.parser.archive.impl.ArchiveHelper.createFile;

@Slf4j
public class TarParser implements ArchiveParser {

    private TarArchiveInputStream zis;
    private final ArchiveValidator archiveValidator;

    public TarParser() {
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
        zis.close();
    }

    private File unzipFile(File zip, String name) throws IOException {
        zis = new TarArchiveInputStream(new FileInputStream(zip));

        TarArchiveEntry tarEntry;
        while ((tarEntry = zis.getNextTarEntry()) != null) {
            if (archiveValidator.isValidEntry(tarEntry.getName(), name)) {
                return createFile(tarEntry.getName(), zis);
            }
        }

        return null;
    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        zis = new TarArchiveInputStream(new FileInputStream(zip));

        TarArchiveEntry tarEntry;
        while ((tarEntry = zis.getNextTarEntry()) != null) {
            if (archiveValidator.isTargetFile(tarEntry.getName())) {
                files.add(createFile(tarEntry.getName(), zis));
            }
        }

        return files;
    }


}
