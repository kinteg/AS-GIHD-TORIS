package ru.iac.ASGIHDTORIS.common.model.fulltable;

public class FullTableModelCreatorImpl {
//
//    List<FullTableModel> fullTableModels;
//
//    @Override
//    public List<FullTableModel> getTableModel() {
//        return null;
//    }
//
//    @Override
//    public void setFullTableModel(List<TableModel> tableModels, List<List<String>> allValues) {
//        this.fullTableModels = createTableModel(tableModels, allValues);
//    }
//
//    @Override
//    public void setFullTableModel(TableModel tableModel, List<String> values) {
//        this.fullTableModels = Collections.singletonList(createTableModel(tableModel, values));
//    }
//
//
//    private FullTableModel createTableModel(TableModel tableModel, List<String> values) {
//        Map<String, String> allValues = new LinkedHashMap<>();
//        List<String> keys = tableModel.getModels().stream().map(DataModel::getKey).collect(Collectors.toList());
//
//        for (int i = 0; i < values.size() && i < keys.size(); i++) {
//            allValues.put(keys.get(i), values.get(i));
//        }
//
//        return new FullTableModel(tableModel, allValues);
//    }
//
//
//    private List<FullTableModel> createTableModel(List<TableModel> tableModels, List<List<String>> allValues) {
//        List<FullTableModel> fullTableModels = new ArrayList<>();
//
//        for (int i = 0; i < tableModels.size() && i < allValues.size(); i++) {
//            fullTableModels.add(createTableModel(tableModels.get(i), allValues.get(i)));
//        }
//
//        return fullTableModels;
//    }

}
