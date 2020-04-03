package ru.iac.ASGIHDTORIS.parser.file.fixer;

import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.io.File;

public interface TableModelFixer {

    TableModel fixTableModel(File file, TableModel tableModel, Reader reader) throws Exception;

}
