package ru.iac.ASGIHDTORIS.parser.archive;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import ru.iac.ASGIHDTORIS.common.TargetFiles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SevenZParser implements ArchiveParser {

    List<File> files;
    byte[] buffer;

    public SevenZParser() {
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File zip) throws IOException {
        return unzipFiles(zip);
    }

    private List<File> unzipFiles(File zip) throws IOException {
        zip.deleteOnExit();
        files = new ArrayList<>();

        SevenZFile sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName())) {
                files.add(createFile(zipEntry.getName(), sevenZFile));
            }
        }

        exist(sevenZFile);

        return files;
    }

    private File createFile(String name, SevenZFile sevenZFile) throws IOException {
        File newFile = new File(name);
        newFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = sevenZFile.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }

    private void exist(SevenZFile sevenZFile) throws IOException {
        sevenZFile.close();
    }

}
