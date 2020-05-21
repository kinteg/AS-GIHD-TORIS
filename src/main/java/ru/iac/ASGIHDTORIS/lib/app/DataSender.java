package ru.iac.ASGIHDTORIS.lib.app;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Map;

@Component
public interface DataSender {

    boolean sendFile(File file, String filename, String tableName);

    boolean sendFiles(File file, Map<String, String> names);

}
