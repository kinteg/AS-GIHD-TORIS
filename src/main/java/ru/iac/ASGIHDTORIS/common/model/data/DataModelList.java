package ru.iac.ASGIHDTORIS.common.model.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class DataModelList {

    private List<String> names;
    private List<String> types;
    private List<Boolean> primaries;

}
