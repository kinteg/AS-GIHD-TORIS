package ru.iac.ASGIHDTORIS.api.db.model;

import lombok.Data;

import java.util.List;

@Data
public class TableModel {

    private String filename;
    private String tableName;

    private List<DataModel> models;

    public TableModel(String filename, String tableName, List<DataModel> models) {
        this.filename = filename;
        this.tableName = tableName;
        this.models = models;
    }
}
