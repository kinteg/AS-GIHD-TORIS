package ru.iac.ASGIHDTORIS.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class FileConverter {

    public static File multipartIntoFile(MultipartFile multipartFile) throws IOException {
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

    public static void delete(File file) {
        String parent = file.getParent();
        file.delete();

        File fileDir = new File(parent);

        if (fileDir.exists()) {
            fileDir.delete();
        }
    }

}
