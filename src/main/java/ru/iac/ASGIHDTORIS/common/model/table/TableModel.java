package ru.iac.ASGIHDTORIS.common.model.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class TableModel {

    private String filename;
    private String tableName;

    private List<DataModel> models;
}
