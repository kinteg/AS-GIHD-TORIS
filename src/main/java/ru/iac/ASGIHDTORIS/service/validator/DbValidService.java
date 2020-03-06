package ru.iac.ASGIHDTORIS.service.validator;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DbValidService {

    boolean isValid(MultipartFile multipartFile, long sourceId) throws IOException;

}
