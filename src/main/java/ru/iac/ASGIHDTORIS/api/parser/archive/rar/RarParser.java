package ru.iac.ASGIHDTORIS.api.parser.archive.rar;

import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RarParser implements ArchiveParser {

    List<File> files;
    byte[] buffer;
    private final TargetFiles targetFiles;

    public RarParser() {
        this.targetFiles = new TargetFiles();
        buffer = new byte[1024];
    }

    @Override
    public List<File> getFiles(File file) throws IOException {
        return null;
    }

    @Override
    public File findByFileName(File file, String filename) throws IOException {
        return null;
    }

}
