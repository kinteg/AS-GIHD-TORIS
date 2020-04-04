package ru.iac.ASGIHDTORIS.parser.archive;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import ru.iac.ASGIHDTORIS.common.TargetFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class TarParser implements ArchiveParser {

    private byte[] buffer;
    private TarArchiveInputStream zis;

    public TarParser() {
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        zis = new TarArchiveInputStream(new FileInputStream(zip));

        TarArchiveEntry zipEntry;
        while ((zipEntry = zis.getNextTarEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName().toLowerCase())) {
                files.add(createFile(zipEntry.getName()));
            }
        }

        return files;
    }

    private File createFile(String name) throws IOException {
        FileCreator fileCreator = new FileCreatorImpl();
        return fileCreator.getFile(name, zis.read(buffer), buffer);
    }

    @Override
    public void close() throws Exception {
        zis.close();
    }
}
