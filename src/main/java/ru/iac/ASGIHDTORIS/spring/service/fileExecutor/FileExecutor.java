package ru.iac.ASGIHDTORIS.spring.service.fileExecutor;

import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.Connection;
import java.util.List;

@Service
public interface FileExecutor {

    void executeFiles(File file, Connection connection, List<String> filenames, List<String> tableNames);

}
