package ru.iac.ASGIHDTORIS.common.model.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TableModelStatus {

    private String status;
    private String exception;
    private String answer;

    private TableModel tableModel;

    public boolean isEmpty() {
        return (status == null || status.equals(""))
                && (exception == null || exception.equals(""))
                && (answer == null || answer.isEmpty());
    }

    public static TableModelStatus emptyTableModelStatus() {
        return TableModelStatus
                .builder()
                .status("")
                .exception("")
                .answer("")
                .tableModel(TableModel.emptyTableModel())
                .build();
    }

}
