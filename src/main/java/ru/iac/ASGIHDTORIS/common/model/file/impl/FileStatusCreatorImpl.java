package ru.iac.ASGIHDTORIS.common.model.file.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.Status;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusCreator;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;

@Component
public class FileStatusCreatorImpl implements FileStatusCreator {

    private static final String EMPTY_FILED = "-";
    private static final String WARN_STATUS = "В табличке больше полей, чем в файле.";
    private static final String ERROR_STATUS = "В табличке меньше полей, чем в файле. Возможна утечка данных.";
    private static final String FILE_NOT_FOUND = "Отсутствует проверяемый файл!";

    @Override
    public FileStatusModel getFileStatusModel(int tableColumnSize, int fileColumnSize, String fileName, String tableName) {
        return createFileStatusModel(tableColumnSize, fileColumnSize, fileName, tableName);
    }

    @Override
    public FileStatusModel getFileNotFoundStatusModel(String fileName, String tableName) {
        return buildFileNotFoundStatus(fileName, tableName);
    }

    private FileStatusModel createFileStatusModel(int tableColumnSize, int fileColumnSize, String fileName, String tableName) {
        String status = getStatus(tableColumnSize, fileColumnSize);

        return build(status, fileName, tableName);

    }

    private String getStatus(int tableColumnSize, int fileColumnSize) {
        if (tableColumnSize == fileColumnSize) {
            return Status.OK;

        } else if (tableColumnSize < fileColumnSize) {
            return Status.ERROR;

        } else {
            return Status.WARN;

        }
    }

    private FileStatusModel build(String status, String fileName, String tableName) {
        if (status.toLowerCase().equals(Status.OK.toLowerCase())) {
            return buildOkStatus(fileName, tableName);

        } else if (status.toLowerCase().equals(Status.WARN.toLowerCase())) {
            return buildWarnStatus(fileName, tableName);

        } else if (status.toLowerCase().equals(Status.ERROR.toLowerCase())) {
         return buildErrorStatus(fileName, tableName);
        }

        return new FileStatusModel();
    }

    private FileStatusModel buildOkStatus(String fileName, String tableName) {
        return FileStatusModel
                .builder()
                .filename(fileName)
                .tableName(tableName)
                .status(Status.OK)
                .error(EMPTY_FILED)
                .warn(EMPTY_FILED)
                .build();
    }

    private FileStatusModel buildWarnStatus(String fileName, String tableName) {
        return FileStatusModel
                .builder()
                .filename(fileName)
                .tableName(tableName)
                .status(Status.WARN)
                .error(EMPTY_FILED)
                .warn(WARN_STATUS)
                .build();
    }

    private FileStatusModel buildErrorStatus(String fileName, String tableName) {
        return FileStatusModel
                .builder()
                .filename(fileName)
                .tableName(tableName)
                .status(Status.ERROR)
                .error(ERROR_STATUS)
                .warn(EMPTY_FILED)
                .build();
    }

    private FileStatusModel buildFileNotFoundStatus(String fileName, String tableName) {
        return FileStatusModel
                .builder()
                .filename(fileName)
                .tableName(tableName)
                .status(Status.NOT_FOUND)
                .error(FILE_NOT_FOUND)
                .warn(EMPTY_FILED)
                .build();
    }

}
