package ru.iac.ASGIHDTORIS.spring.service.fileExecutor.impl;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.lib.app.DataSender;
import ru.iac.ASGIHDTORIS.lib.app.impl.DataSenderImpl;
import ru.iac.ASGIHDTORIS.spring.service.fileExecutor.FileExecutor;

import java.io.File;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class FileExecutorImpl implements FileExecutor {

    @Override
    public void executeFiles(File file, Connection connection, List<String> filenames, List<String> tableNames) {
        DataSender dataSender = new DataSenderImpl(connection);

        dataSender.sendFiles(file, createMap(filenames, tableNames));
    }

    private Map<String, String> createMap(List<String> filenames, List<String> tableNames) {
        Map<String, String> map = new LinkedHashMap<>();

        for (int i = 0; i < filenames.size() && i < tableNames.size(); i++) {
            map.put(filenames.get(i), tableNames.get(i));
        }

        return map;
    }
}
