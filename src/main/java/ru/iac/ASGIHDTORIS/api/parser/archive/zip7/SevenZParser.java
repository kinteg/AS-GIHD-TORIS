package ru.iac.ASGIHDTORIS.api.parser.archive.zip7;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SevenZParser implements ArchiveParser {

    List<File> files;
    byte[] buffer;
    private final TargetFiles targetFiles;

    public SevenZParser() {
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

        SevenZFile sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;
        File file = null;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (targetFiles.isTargetFile(zipEntry.getName())
                    && zipEntry.getName().equals(filename)) {

                file = createFile(zipEntry.getName(), sevenZFile);
            }
        }

        exist(sevenZFile, zip);

        return file;
    }

    private List<File> unzipFiles(File zip) throws IOException {
        zip.deleteOnExit();
        files = new ArrayList<>();

        SevenZFile sevenZFile = new SevenZFile(zip);
        SevenZArchiveEntry zipEntry;

        while ((zipEntry = sevenZFile.getNextEntry()) != null) {
            if (targetFiles.isTargetFile(zipEntry.getName())) {
                files.add(createFile(zipEntry.getName(), sevenZFile));
            }
        }

        exist(sevenZFile, zip);

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

    private void exist(SevenZFile sevenZFile, File zip) throws IOException {
        sevenZFile.close();
        zip.delete();
    }

}
