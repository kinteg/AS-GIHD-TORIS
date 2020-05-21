package ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.impl;

import ru.iac.ASGIHDTORIS.lib.lib.common.factory.FileFactory;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.impl.FileFactoryImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.FileExtension;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFile;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.TableModelFixer;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.impl.TableModelFixerImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.provider.DataProvider;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.provider.impl.DataProviderImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileParserImpl implements FileParser {

    private final TableModelFixer tableModelFixer;
    private final DataProvider provider;
    private final FileFactory fileFactory;
    private final TargetFile targetFile;

    public FileParserImpl() {
        this.tableModelFixer = new TableModelFixerImpl();
        provider = new DataProviderImpl();
        fileFactory = new FileFactoryImpl();
        targetFile = new TargetFileImpl();
    }

    @Override
    public List<FullTableModel> getFullTable(List<File> files, long limit) {
        return files.stream()
                .map(file -> getFullTable(file, limit))
                .collect(Collectors.toList());
    }

    @Override
    public FullTableModel getFullTable(File file, long limit) {
        return getFullTable(file, limit, new TableModel());
    }

    @Override
    public FullTableModel getFullTable(File file, long limit, TableModel tableModel) {
        fixTableModel(file, tableModel);
        List<Map<String, String>> values = getValues(file, limit, tableModel);

        return FullTableModel
                .builder()
                .tableModel(tableModel)
                .values(values)
                .build();
    }

    private void fixTableModel(File file, TableModel tableModel) {
        Reader reader = createReader(file);
        tableModelFixer.fixTableModel(file, tableModel, reader);
    }

    private List<Map<String, String>> getValues(File file, long limit, TableModel tableModel) {
        Reader reader = createReader(file);
        List<String> keys = getKeys(tableModel);

        return provider.getValues(reader, keys, limit);
    }

    private Reader createReader(File file) {
        FileExtension extension = targetFile.getExtension(file.getName());
        return fileFactory.getFileReader(extension, file);
    }

    private List<String> getKeys(TableModel tableModel) {
        return tableModel.getModels().stream()
                .map(DataModel::getKey)
                .collect(Collectors.toList());
    }
}
