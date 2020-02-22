package ru.iac.ASGIHDTORIS.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface DBService {

    String parseIntoBd(MultipartFile multipartFile, String tableInfo) throws IOException, SQLException;

}
