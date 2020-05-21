package ru.iac.ASGIHDTORIS.spring.service.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Override
    public File convertFile(MultipartFile multipartFile) {
        try {
            return multipartIntoFile(multipartFile);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    private File multipartIntoFile(MultipartFile multipartFile) throws IOException {
        File fileDir = new File(UUID.randomUUID().toString());
        fileDir.mkdirs();
        fileDir.deleteOnExit();
        File file = new File(fileDir + File.separator + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        file.deleteOnExit();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return file;
    }

    @Override
    public void delete(File file) {
        String parent = file.getParent();
        file.delete();

        File fileDir = new File(parent);

        if (fileDir.exists()) {
            fileDir.delete();
        }
    }

}
