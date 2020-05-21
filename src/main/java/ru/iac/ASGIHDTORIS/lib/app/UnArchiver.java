package ru.iac.ASGIHDTORIS.lib.app;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public interface UnArchiver {

    List<File> unArchiveFiles(File file);

    File unArchiveFile(File file, String filename);

}
