package ru.iac.ASGIHDTORIS.parser.archive;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCreatorImpl implements FileCreator {

    @Override
    public File getFile(String name, int read, byte[] buffer) throws IOException {
        File newFile = new File(name);
        newFile.deleteOnExit();

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = read) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }
}
