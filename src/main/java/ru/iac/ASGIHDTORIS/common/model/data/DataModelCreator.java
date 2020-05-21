package ru.iac.ASGIHDTORIS.common.model.data;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;

import java.util.List;

public interface DataModelCreator {

    List<DataModel> getDataModel();

    void setDataModel(DataModelList dataModelList);

}
