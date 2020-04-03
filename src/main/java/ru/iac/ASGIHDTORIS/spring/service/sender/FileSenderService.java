package ru.iac.ASGIHDTORIS.spring.service.sender;

import org.springframework.stereotype.Service;

import java.io.File;

@Service
public interface FileSenderService {

    boolean sendFile(String name, File file);

}
