package ru.iac.ASGIHDTORIS.api.db.model.data;

import java.util.ArrayList;
import java.util.List;

public class DataModelCreatorImpl implements DataModelCreator {

    List<DataModel> dataModels;

    @Override
    public List<DataModel> getDataModel() {
        return dataModels;
    }

    @Override
    public void setDataModel(List<String> names, List<String> types, List<Boolean> primaries) {
        this.dataModels = createDataModels(names, types, primaries);
    }

    private List<DataModel> createDataModels(List<String> names, List<String> types, List<Boolean> primaries) {
        List<DataModel> dataModels = new ArrayList<>();

        for (int i = 0; i < names.size() && i < types.size() && i < primaries.size(); i++) {
            dataModels.add(createDataModel(names.get(i), types.get(i), primaries.get(i)));
        }

        return dataModels;
    }

    private DataModel createDataModel(String name, String type, boolean primary) {
        return new DataModel(name, type, primary);
    }
}
