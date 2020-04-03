package ru.iac.ASGIHDTORIS.common.model.data;

import java.util.ArrayList;
import java.util.List;

public class DataModelCreatorImpl implements DataModelCreator {

    List<DataModel> dataModels;

    @Override
    public List<DataModel> getDataModel() {
        return dataModels;
    }

    @Override
    public void setDataModel(DataModelList dataModelList) {
        this.dataModels = createDataModels(
                dataModelList.getNames(),
                dataModelList.getTypes(),
                dataModelList.getPrimaries());
    }

    private List<DataModel> createDataModels(List<String> names, List<String> types, List<Boolean> primaries) {
        List<DataModel> dataModels = new ArrayList<>();

        for (int i = 0; i < names.size() && i < types.size() && i < primaries.size(); i++) {
            dataModels.add(createDataModel(names.get(i).trim(), types.get(i).trim(), primaries.get(i)));
        }

        return dataModels;
    }

    private DataModel createDataModel(String name, String type, boolean primary) {
        return new DataModel(name, type, primary);
    }
}
