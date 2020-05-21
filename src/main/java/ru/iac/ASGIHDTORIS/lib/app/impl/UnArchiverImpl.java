package ru.iac.ASGIHDTORIS.lib.app.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.app.UnArchiver;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.ArchiveFactory;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.impl.ArchiveFactoryImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.archive.ArchiveExtension;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.archive.TargetArchive;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.archive.TargetArchiveImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFile;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.archive.ArchiveParser;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UnArchiverImpl implements UnArchiver {

    private final TargetArchive targetArchive;
    private final ArchiveFactory archiveFactory;
    private final TargetFile targetFile;

    public UnArchiverImpl() {
        targetFile = new TargetFileImpl();
        targetArchive = new TargetArchiveImpl();
        archiveFactory = new ArchiveFactoryImpl();
    }

    @Override
    public List<File> unArchiveFiles(File file) {
        if (file == null) {
            return Collections.emptyList();
        }

        List<File> files;

        if (targetArchive.isTargetArchive(file.getName())) {
            ArchiveExtension extension = targetArchive.getExtension(file.getName());
            files = getFiles(extension, file);

        } else {
            files = Collections.singletonList(file);
        }

        return files;
    }

    private List<File> getFiles(ArchiveExtension extension, File file) {
        List<File> files;

        try (ArchiveParser parser = archiveFactory.getArchiveParser(extension)) {
            files = parser.getFiles(file);
            files = fixFiles(files);
        } catch (Exception e) {
            files = Collections.emptyList();
        }

        return files;
    }

    @Override
    public File unArchiveFile(File file, String filename) {
        if (file == null) {
            return null;
        }

        File findFile;

        if (targetArchive.isTargetArchive(file.getName())) {
            ArchiveExtension extension = targetArchive.getExtension(file.getName());
            findFile = getFile(extension, file, filename);
        } else if (file.getName().equals(filename)) {
            findFile = file;
        } else {
            findFile = null;
        }

        return findFile;
    }

    private File getFile(ArchiveExtension extension, File file, String filename) {
        File findFile;

        try (ArchiveParser parser = archiveFactory.getArchiveParser(extension)) {
            findFile = parser.getFile(file, filename);

        } catch (Exception e) {
            findFile = null;
        }

        return findFile;
    }

    private List<File> fixFiles(List<File> files) {
        return files.stream()
                .filter(file -> targetFile.isTargetFile(file.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteFiles(List<File> files) {
        for (File file :
                files) {
            deleteFile(file);
        }
    }

    @Override
    public void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }
}
