package ru.iac.ASGIHDTORIS.parser.archive;

import lombok.extern.slf4j.Slf4j;
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

    List<File> files;
    byte[] buffer;

    public ZipParser() {
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    private List<File> unzipFiles(File zip) throws IOException {
        zip.deleteOnExit();
        files = new ArrayList<>();

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip), StandardCharsets.UTF_8);
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName().toLowerCase())) {
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
