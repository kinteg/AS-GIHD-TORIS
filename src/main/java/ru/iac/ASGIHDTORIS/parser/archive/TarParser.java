package ru.iac.ASGIHDTORIS.parser.archive;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
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
public class TarParser implements ArchiveParser {

    private byte[] buffer;
    private TarArchiveInputStream zis;

    public TarParser() {
        buffer = new byte[1024];
    }

    @Override
    public File getFile(File zip, String name) throws IOException {
        zis = new TarArchiveInputStream(new FileInputStream(zip));

        TarArchiveEntry tarEntry;
        while ((tarEntry = zis.getNextTarEntry()) != null) {
            if (TargetFiles.isTargetFile(tarEntry.getName().toLowerCase())
                    && tarEntry.getName().toLowerCase().equals(name.toLowerCase())) {
                return createFile(tarEntry.getName(), zis);
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

        zis = new TarArchiveInputStream(new FileInputStream(zip));

        TarArchiveEntry tarEntry;
        while ((tarEntry = zis.getNextTarEntry()) != null) {
            if (TargetFiles.isTargetFile(tarEntry.getName().toLowerCase())) {
                files.add(createFile(tarEntry.getName(), zis));
            }
        }

        return files;
    }

    private File createFile(String name, TarArchiveInputStream zis) throws IOException {
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
        zis.close();
    }
}
