package ru.iac.ASGIHDTORIS.spring.service.dataSender;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface DataSenderService {

    boolean sendData(MultipartFile multipartFile, Long id) throws IOException;

    boolean sendDates(MultipartFile multipartFile, Long id) throws IOException;

}
