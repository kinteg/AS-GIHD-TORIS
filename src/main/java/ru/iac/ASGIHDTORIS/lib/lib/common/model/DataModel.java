package ru.iac.ASGIHDTORIS.lib.lib.common.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder(toBuilder = true)
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

    public static DataModel createEmptyDataModel() {
        return DataModel
                .builder()
                .key("")
                .type("")
                .primary(false)
                .build();
    }

}
