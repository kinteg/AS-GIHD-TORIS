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

    private byte[] buffer;
    private SevenZFile sevenZFile;

    public SevenZParser() {
        buffer = new byte[1024];
    }

    @Override
    public File getFile(File zip, String name) throws IOException {
        sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName())
                    && zipEntry.getName().toLowerCase().equals(name.toLowerCase())) {
                return createFile(zipEntry.getName(), sevenZFile);
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

        sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (TargetFiles.isTargetFile(zipEntry.getName())) {
                files.add(createFile(zipEntry.getName(), sevenZFile));
            }
        }

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

    @Override
    public void close() throws Exception {
        sevenZFile.close();
    }
}