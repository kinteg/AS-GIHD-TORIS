package ru.iac.ASGIHDTORIS.parser.archive.impl;

import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;

class ArchiveHelper {

    static File createFile(String name, TarArchiveInputStream zis) throws IOException {
        File newFile = new File(name);
        newFile.deleteOnExit();
        byte[] buffer = new byte[1024];

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }

    static File createFile(String name, ZipInputStream zis) throws IOException {
        File newFile = new File(name);
        newFile.deleteOnExit();
        byte[] buffer = new byte[1024];

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }

    static File createFile(String name, SevenZFile sevenZFile) throws IOException {
        File newFile = new File(name);
        newFile.deleteOnExit();
        byte[] buffer = new byte[1024];

        FileOutputStream fos = new FileOutputStream(newFile);

        int len;

        while ((len = sevenZFile.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();

        return newFile;
    }

}
