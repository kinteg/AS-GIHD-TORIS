package ru.iac.ASGIHDTORIS.service.sender;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

public interface DbService {

    String sendData(MultipartFile multipartFile, String nameFile, String nameTable, long sourceId) throws IOException, SQLException;

}
