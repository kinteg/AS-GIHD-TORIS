package ru.iac.ASGIHDTORIS.spring.service.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.FileNameUtil;
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
                }

            } catch (IOException e) {
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
    public File convertFile(MultipartFile multipartFile) {
        try {
            return FileConverter.multipartIntoFile(multipartFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

}
