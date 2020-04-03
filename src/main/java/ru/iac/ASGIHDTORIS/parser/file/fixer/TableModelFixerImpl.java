package ru.iac.ASGIHDTORIS.parser.file.fixer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.text.RandomStringGenerator;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.common.validator.string.TableNameValidator;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.io.File;
import java.util.List;

public class TableModelFixerImpl implements TableModelFixer {

    private final Validator tableNameValidator;
    private final RandomStringGenerator generator;

    public TableModelFixerImpl() {
        this.tableNameValidator = new TableNameValidator();
        generator = new RandomStringGenerator
                .Builder()
                .withinRange('a', 'z')
                .build();
    }

    @Override
    public TableModel fixTableModel(File file, TableModel tableModel, Reader reader) throws Exception {

        if (tableModel.getModels() == null) {
            tableModel.setModels(getNamesColumn(reader));
        }

        if (tableModel.getTableName() == null) {
            tableModel.setFilename(file.getName());

            String name = FilenameUtils.getBaseName(file.getAbsolutePath());

            if (tableNameValidator.isValid(name)) {
                tableModel.setTableName(name);
            } else {
                tableModel.setTableName(generator.generate(15));
            }

        }

        return tableModel;

    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        List<String> nameColumns = reader.readNext();

        return ColumnCreator.createColumns(nameColumns);
    }

}
