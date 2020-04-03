package ru.iac.ASGIHDTORIS.common.model.data;

import lombok.Data;

import java.util.List;

@Data
public class DataModelList {

    private List<String> names;
    private List<String> types;
    private List<Boolean> primaries;

}
