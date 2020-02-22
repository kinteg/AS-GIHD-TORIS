package ru.iac.ASGIHDTORIS.api.db;

import lombok.Data;

@Data
public class DataModel {

    private String key;
    private String type;
    private boolean primary;

    public DataModel(String key, String type) {
        this.key = key;
        this.type = type;
    }

    public DataModel(String key, String type, boolean primary) {
        this.key = key;
        this.type = type;
        this.primary = primary;
    }

}
