package ru.iac.ASGIHDTORIS.parser.archive.impl;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.TargetFiles;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

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
    public File getFile(File zip, String name) throws IOException {
        zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName().toLowerCase())
                    && zipEntry.getName().toLowerCase().equals(name.toLowerCase())) {

                return createFile(zipEntry.getName(), zis);
            }
        }

        return null;
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
        File newFile = new File(name);
        newFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }

    @Override
    public void close() throws Exception {
        zis.closeEntry();
        zis.close();
    }
}