package ru.iac.ASGIHDTORIS.lib.lib.parser.file.provider.impl;

import ru.iac.ASGIHDTORIS.lib.lib.parser.file.provider.DataProvider;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataProviderImpl implements DataProvider {
    @Override
    public List<Map<String, String>> getValues(Reader reader, List<String> keys, long limit) {
        List<String> nextRecordArray;
        List<Map<String, String>> values = correctFirstRow(reader, keys);

        for (int j = 0; !(nextRecordArray = reader.readNext()).isEmpty() && j < limit; j++) {

            values.add(getRow(keys, nextRecordArray));
        }

        return values;
    }

    private List<Map<String, String>> correctFirstRow(Reader reader, List<String> keys) {
        List<String> nextRecordArray;
        List<Map<String, String>> values = new ArrayList<>();

        if (!(nextRecordArray = reader.readNext()).isEmpty()) {
            if (!keys.equals(nextRecordArray)) {
                values.add(getRow(keys, nextRecordArray));
            }
        }

        return values;
    }

    private Map<String, String> getRow(List<String> keys, List<String> nextRecordArray) {
        Map<String, String> row = new LinkedHashMap<>();

        for (int i = 0; i < keys.size() && i < nextRecordArray.size(); i++) {
            row.put(keys.get(i), nextRecordArray.get(i));
        }

        return row;
    }
}
