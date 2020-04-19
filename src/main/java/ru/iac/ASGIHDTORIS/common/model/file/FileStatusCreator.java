package ru.iac.ASGIHDTORIS.common.model.file;

import org.springframework.stereotype.Component;

@Component
public interface FileStatusCreator {

    FileStatusModel getFileStatusModel(int tableColumnSize, int fileColumnSize,
                                       String fileName, String tableName);

    FileStatusModel getFileNotFoundStatusModel(String fileName, String tableName);
}
