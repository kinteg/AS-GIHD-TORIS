package ru.iac.ASGIHDTORIS.api.parser.archive.zip;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipParser implements ArchiveParser {

    List<File> files;
    byte[] buffer;
    private final TargetFiles targetFiles;

    public ZipParser() {
        this.targetFiles = new TargetFiles();
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    @Override
    public File findByFileName(File zip, String filename) throws IOException {
        return unzipFile(zip, filename);
    }

    private File unzipFile(File zip, String filename) throws IOException {
        zip.deleteOnExit();

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry;
        File file = null;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (targetFiles.isTargetFile(zipEntry.getName())
                    && zipEntry.getName().equals(filename)) {

                file = createFile(zipEntry.getName(), zis);
            }
        }

        exist(zis, zip);

        return file;
    }

    private List<File> unzipFiles(File zip) throws IOException {
        zip.deleteOnExit();
        files = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (targetFiles.isTargetFile(zipEntry.getName())) {
                files.add(createFile(zipEntry.getName(), zis));
            }
        }

        exist(zis, zip);

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

    private void exist(ZipInputStream zis, File zip) throws IOException {
        zis.closeEntry();
        zis.close();
        zip.delete();
    }

}
