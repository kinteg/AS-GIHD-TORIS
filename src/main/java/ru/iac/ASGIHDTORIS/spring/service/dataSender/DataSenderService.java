package ru.iac.ASGIHDTORIS.spring.service.dataSender;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;

import java.io.IOException;
import java.util.List;

@Service
public interface DataSenderService {

    boolean sendData(MultipartFile multipartFile, Long id) throws IOException;

    boolean sendDates(MultipartFile multipartFile, Long id) throws IOException;

}
