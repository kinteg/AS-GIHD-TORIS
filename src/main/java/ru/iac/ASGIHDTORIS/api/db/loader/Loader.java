package ru.iac.ASGIHDTORIS.api.db.loader;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.io.File;
import java.util.List;

public interface Loader {

    String SQL_INSERT =
            "INSERT INTO ${table} (${keys}) VALUES (${values}) ";

    String SQL_UPDATE =
            "ON CONFLICT (${id}) DO UPDATE SET ${keys_values}";

    String TABLE_REGEX = "\\$\\{table\\}";
    String KEYS_REGEX = "\\$\\{keys\\}";
    String VALUES_REGEX = "\\$\\{values\\}";
    String ID_REGEX = "\\$\\{id\\}";
    String KEYS_VALUES_REGEX = "\\$\\{keys_values\\}";

    boolean insert(File file, String tableName, List<DataModel> keys);

}
