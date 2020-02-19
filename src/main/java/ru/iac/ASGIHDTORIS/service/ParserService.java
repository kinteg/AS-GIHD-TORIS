package ru.iac.ASGIHDTORIS.service;

import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@Service
public interface ParserService {

    String getWithParser(MultipartFile multipartFile, long limit) throws IOException, CsvValidationException;

    String parseIntoBd(MultipartFile multipartFile, String tableInfo) throws IOException, CsvValidationException, SQLException;

}
