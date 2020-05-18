package ru.iac.ASGIHDTORIS.lib.lib.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FullTableModel {

    private TableModel tableModel;
    private List<Map<String, String>> values;

    public static FullTableModel emptyFullTableModel() {
        return FullTableModel
                .builder()
                .tableModel(TableModel.emptyTableModel())
                .values(Collections.emptyList())
                .build();
    }

}
