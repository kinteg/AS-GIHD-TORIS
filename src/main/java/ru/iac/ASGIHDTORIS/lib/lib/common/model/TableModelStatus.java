package ru.iac.ASGIHDTORIS.lib.lib.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iac.ASGIHDTORIS.lib.lib.common.Status;

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

    public static TableModelStatus createOkStatus(TableModel tableModel) {
        return TableModelStatus
                .builder()
                .status(Status.OK)
                .exception("")
                .answer("Таблица создана")
                .tableModel(tableModel)
                .build();
    }

    public static TableModelStatus createErrorStatus(Exception exception) {
        return TableModelStatus
                .builder()
                .status(Status.ERROR)
                .exception(exception.getMessage())
                .answer("Таблица не создана")
                .tableModel(TableModel.emptyTableModel())
                .build();
    }

}
