package ru.iac.ASGIHDTORIS.common.model.table;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TableModelCreatorFromDb {

    List<TableModel> createTableModels(long patternId);

}
