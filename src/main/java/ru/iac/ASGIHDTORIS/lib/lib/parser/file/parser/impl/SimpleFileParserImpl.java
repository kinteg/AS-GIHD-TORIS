package ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.FileFactory;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.impl.FileFactoryImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.FileExtension;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFile;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.TableModelFixer;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.impl.TableModelFixerImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.SimpleFileParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleFileParserImpl implements SimpleFileParser {

    private final TableModelFixer tableModelFixer;
    private final FileFactory fileFactory;
    private final TargetFile targetFile;

    public SimpleFileParserImpl() {
        this.tableModelFixer = new TableModelFixerImpl();
        fileFactory = new FileFactoryImpl();
        targetFile = new TargetFileImpl();
    }

    @Override
    public List<TableModel> getFullTable(List<File> files) {
        return files.stream()
                .map(this::getFullTable)
                .collect(Collectors.toList());
    }

    @Override
    public TableModel getFullTable(File file) {
        return fixTableModel(file);
    }

    private TableModel fixTableModel(File file) {
        Reader reader = createReader(file);
        return tableModelFixer.fixTableModel(file, reader);
    }

    private Reader createReader(File file) {
        FileExtension extension = targetFile.getExtension(file.getName());
        return fileFactory.getFileReader(extension, file);
    }
}
