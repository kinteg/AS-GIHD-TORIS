package ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;

import java.util.List;

public interface ColumnCreator {

    List<DataModel> createColumns(List<String> nameColumns);

}
