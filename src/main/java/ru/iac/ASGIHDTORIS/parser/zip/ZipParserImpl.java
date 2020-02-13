package ru.iac.ASGIHDTORIS.parser.zip;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.api.TargetFiles;

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

    private final TargetFiles targetFiles;

    public ZipParserImpl() {
        this.targetFiles = new TargetFiles();
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }


    private List<File> unzipFiles(File zip) throws IOException {
        List<File> files = new ArrayList<>();
        byte[] buffer = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
        ZipEntry zipEntry;

        while ((zipEntry = zis.getNextEntry()) != null) {
            log.info("type: " + zipEntry.getName());

            if (!targetFiles.isTargetFile(zipEntry.getName())) {
                break;
            }

            File newFile = new File(zipEntry.getName());
            newFile.deleteOnExit();


            FileOutputStream fos = new FileOutputStream(newFile);

            int len;

            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }

            fos.close();
            files.add(newFile);
        }

        zis.closeEntry();
        zis.close();

        return files;
    }


}
