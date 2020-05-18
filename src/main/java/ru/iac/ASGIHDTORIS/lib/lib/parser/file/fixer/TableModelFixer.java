package ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.File;

public interface TableModelFixer {

    TableModel fixTableModel(File file, Reader reader);

    TableModel fixTableModel(File file, TableModel tableModel, Reader reader);

}
