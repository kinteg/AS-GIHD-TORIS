package ru.iac.ASGIHDTORIS.parser.file;

import lombok.extern.slf4j.Slf4j;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.parser.file.type.TypeGenerator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class FileReader {

    private final Reader reader;

    public FileReader(Reader reader) {
        this.reader = reader;
    }

    public FullTableModel createTableModel(TableModel tableModel, long limit) throws Exception {
        return getWithLimit(tableModel, limit);
    }

    private FullTableModel getWithLimit(TableModel tableModel, long limit) throws Exception {
        if (reader == null) {
            return new FullTableModel();
        }

        List<String> keys = tableModel.getModels().stream().map(DataModel::getKey).collect(Collectors.toList());
        List<Map<String, String>> values = createValues(keys, limit);
        List<String> types = createType(values);

        for (int i = 0; i < tableModel.getModels().size() && i < types.size(); i++) {
            tableModel.getModels().get(i).setType(types.get(i));
        }

        return FullTableModel.builder()
                .tableModel(tableModel)
                .values(values)
                .build();
    }

    private List<Map<String, String>> createValues(List<String> keys, long limit) throws Exception {
        List<String> nextRecordArray;
        List<Map<String, String>> values = new ArrayList<>();

        for (int j = 0; (nextRecordArray = reader.readNext()) != null && j < limit; j++) {
            Map<String, String> map = new LinkedHashMap<>();

            for (int i = 0; i < keys.size() && i < nextRecordArray.size(); i++) {
                map.put(keys.get(i), nextRecordArray.get(i));
            }

            values.add(map);
        }

        return values;
    }

    private List<String> createType(List<Map<String, String>> values) {
        List<String> types = new ArrayList<>();
        List<String> keys = new ArrayList<>(values.get(0).keySet());

        for (String key :
                keys) {
            List<String> column = new ArrayList<>();

            for (Map<String, String> row :
                    values) {
                column.add(row.get(key));
            }

            types.add(TypeGenerator.type(column));
        }


        return types;
    }


}
