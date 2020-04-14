package ru.iac.ASGIHDTORIS.spring.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Service
public interface FileService {

    List<File> getFiles(File file);

    File getFile(File file, String fileName);

    File convertFile(MultipartFile multipartFile);

}
