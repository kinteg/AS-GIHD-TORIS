package ru.iac.ASGIHDTORIS.common.model.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class FileStatusModel {

    private String tableName;
    private String filename;

    private String status;
    private String warn;
    private String error;

}
