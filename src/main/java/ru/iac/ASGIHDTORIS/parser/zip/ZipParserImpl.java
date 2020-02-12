package ru.iac.ASGIHDTORIS.parser.zip;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
public class ZipParserImpl implements ZipParser {

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }


    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry = zis.getNextEntry();

        while (zipEntry != null) {
            log.info("type: " + zipEntry.getName());
            File newFile = new File(zipEntry.getName());
            newFile.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(newFile);

            int len;

            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
            log.info("size: " + newFile.length());
            files.add(newFile);
        }
        zis.closeEntry();
        zis.close();

        return files;
    }

}
