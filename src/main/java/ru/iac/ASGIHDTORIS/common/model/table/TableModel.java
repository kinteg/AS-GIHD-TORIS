package ru.iac.ASGIHDTORIS.common.model.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;

import java.util.Collections;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TableModel {

    private String filename;
    private String tableName;
    private String primaryKey;
    private List<DataModel> models;

    public TableModel(String filename, String tableName, List<DataModel> models) {
        this.filename = filename;
        this.tableName = tableName;
        this.models = models;
    }

    public boolean isEmpty() {
        return (filename == null || filename.equals(""))
                && (tableName == null || tableName.equals(""))
                && (models == null || models.isEmpty());
    }

    public static TableModel emptyTableModel() {
        return TableModel
                .builder()
                .filename("")
                .tableName("")
                .models(Collections.emptyList())
                .build();
    }
}
