package ru.iac.ASGIHDTORIS.common.model.fulltable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullTableModel that = (FullTableModel) o;
        return values.get(0).values().toArray()[0].equals(that.values.get(0).values().toArray()[0]);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableModel, values);
    }

}
