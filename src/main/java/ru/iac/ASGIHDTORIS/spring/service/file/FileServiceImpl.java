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
        List<File> files;

        if (TargetFiles.isArchive(file.getName())) {
            ArchiveParser parser = ArchiveFactory.getParser(FilenameUtils.getExtension(file.getName()));

            try {

                if (parser == null) {
                    files = Collections.emptyList();
                } else {
                    files = parser.getFiles(file);
                    parser.close();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
                files = Collections.emptyList();
            } finally {
                file.delete();
            }

        } else {
            files = Collections.singletonList(file);
        }

        return files;
    }

    @Override
    public File getFile(File file, String fileName) {
        File findFile;

        if (TargetFiles.isArchive(file.getName())) {
            ArchiveParser parser = ArchiveFactory.getParser(FilenameUtils.getExtension(file.getName()));

            try {

                if (parser == null) {
                    findFile = null;
                } else {
                    findFile = parser.getFile(file, fileName);
                    parser.close();
                }

            } catch (Exception e) {
                log.error(e.getMessage());
                findFile = null;
            } finally {
                file.delete();
            }

        } else {
            findFile = file;
        }

        return findFile;
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

}
