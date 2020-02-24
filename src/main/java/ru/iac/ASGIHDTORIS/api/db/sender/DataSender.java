package ru.iac.ASGIHDTORIS.api.db.sender;

import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.io.File;
import java.util.List;

public interface DataSender {

    boolean send(File file, List<DataModel> models, String nameTable);

}
