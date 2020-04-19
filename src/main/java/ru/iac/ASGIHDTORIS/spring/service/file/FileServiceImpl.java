package ru.iac.ASGIHDTORIS.spring.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.FileConverter;
import ru.iac.ASGIHDTORIS.common.TargetFiles;
import ru.iac.ASGIHDTORIS.common.factory.ArchiveFactory;
import ru.iac.ASGIHDTORIS.parser.archive.ArchiveParser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public List<File> getFiles(File file) {
        String extension = FilenameUtils.getExtension(file.getName());

        return getFiles(file, extension);
    }

    @Override
    public File getFile(File file, String fileName) {
        String extension = FilenameUtils.getExtension(file.getName());

        return getFile(file, fileName, extension);
    }

    @Override
    public File convertFile(MultipartFile multipartFile) {
        try {
            return FileConverter.multipartIntoFile(multipartFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private List<File> getFiles(File file, String extension) {

        if (TargetFiles.isArchive(file.getName())) {

            return getFilesFromArchive(file, extension);

        }

        return Collections.singletonList(file);
    }

    private File getFile(File file, String fileName, String extension) {
        if (TargetFiles.isArchive(file.getName())) {

            return getFileFromArchive(file, fileName, extension);

        } else if (file.getName().equals(fileName)) {

            return file;
        }

        return null;

    }

    private List<File> getFilesFromArchive(File file, String extension) {
        List<File> findFiles;

        try (ArchiveParser parser = ArchiveFactory.getParser(extension)) {

            findFiles = parser.getFiles(file);
        } catch (Exception e) {

            log.error(e.getMessage());
            findFiles = Collections.emptyList();
            ;
        }

        return findFiles;
    }

    private File getFileFromArchive(File file, String fileName, String extension) {
        File findFile;

        try (ArchiveParser parser = ArchiveFactory.getParser(extension)) {

            findFile = parser.getFile(file, fileName);
        } catch (Exception e) {

            log.error(e.getMessage());
            findFile = null;
        }

        return findFile;
    }


}
