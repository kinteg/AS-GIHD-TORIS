package ru.iac.ASGIHDTORIS.api.db.sender;

import ru.iac.ASGIHDTORIS.api.db.model.DataModel;

import java.util.List;

public interface DataSender {

    boolean send(List<DataModel> models, String nameTable);

}
