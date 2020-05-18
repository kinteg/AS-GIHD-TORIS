package ru.iac.ASGIHDTORIS.lib.lib.parser.archive.impl.reader;

import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveHelper;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ArchiveHelperImpl<T extends ArchiveReader> implements ArchiveHelper<T> {
    @Override
    public File createFile(String name, T zis) {
        File newFile = new File(name);
        newFile.deleteOnExit();
        byte[] buffer = new byte[1024];

        try (FileOutputStream fos = new FileOutputStream(newFile)) {
            int len;

            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile;
    }
}
