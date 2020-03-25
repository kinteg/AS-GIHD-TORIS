package ru.iac.ASGIHDTORIS.common.model.data;

import lombok.Data;

@Data
public class DataModel {

    private String key;
    private String type;
    private boolean primary;

    public DataModel(String key) {
        this(key, "TEXT");
    }

    public DataModel(String key, String type) {
        this(key, type, false);
    }

    public DataModel(String key, boolean primary) {
        this(key, "TEXT", primary);
    }

    public DataModel(String key, String type, boolean primary) {
        this.key = key;
        this.type = type;
        this.primary = primary;
    }

}
