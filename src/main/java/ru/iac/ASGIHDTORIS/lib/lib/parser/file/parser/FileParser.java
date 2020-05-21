package ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

import java.io.File;
import java.util.List;

public interface FileParser {

    List<FullTableModel> getFullTable(List<File> files, long limit);

    FullTableModel getFullTable(File file, long limit);

    FullTableModel getFullTable(File file, long limit, TableModel tableModel);

}
