package ru.iac.ASGIHDTORIS.api.db.loader;

import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.io.File;
import java.util.List;

public interface Loader {

    boolean insert(File file, String tableName, List<DataModel> keys);

}
