package ru.iac.ASGIHDTORIS.api.db.model.data;

import java.util.List;

public interface DataModelCreator {

    List<DataModel> getDataModel();

    void setDataModel(List<String> names, List<String> types, List<Boolean> primaries);

}
