package ru.iac.ASGIHDTORIS.api.db.model.table;

import lombok.Data;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.Collections;
import java.util.List;

@Data
public class TableModel {

    private String filename;
    private String tableName;

    private List<DataModel> models;

    public TableModel() {
    }

    public TableModel(String filename, String tableName) {
        this(filename, tableName, Collections.emptyList());
    }

    public TableModel(String filename, String tableName, List<DataModel> models) {
        this.filename = filename;
        this.tableName = tableName;
        this.models = models;
    }
}
