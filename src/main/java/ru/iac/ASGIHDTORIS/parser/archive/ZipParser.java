package ru.iac.ASGIHDTORIS.parser.archive;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import ru.iac.ASGIHDTORIS.common.TargetFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipParser implements ArchiveParser {

    private byte[] buffer;
    private ZipInputStream zis;

    public ZipParser() {
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();

        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);

        ZipEntry zipEntry;
        while ((zipEntry = zis.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName().toLowerCase())) {
                files.add(createFile(zipEntry.getName(), zis));
            }
        }

        return files;
    }

    private File createFile(String name, ZipInputStream zis) throws IOException {
        FileCreator fileCreator = new FileCreatorImpl();
        return fileCreator.getFile(name, zis.read(buffer), buffer);
    }

    @Override
    public void close() throws Exception {
        zis.closeEntry();
        zis.close();
    }
}
