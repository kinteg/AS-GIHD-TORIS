package ru.iac.ASGIHDTORIS.service.parser.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface ParserService {

    String getWithParser(MultipartFile multipartFile, long limit, long sourceId) throws Exception;

}
