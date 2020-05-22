package ru.iac.ASGIHDTORIS.lib.app.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.app.CommonFileParser;
import ru.iac.ASGIHDTORIS.lib.app.UnArchiver;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.impl.FileParserImpl;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommonFileParserImpl implements CommonFileParser {

    private final long CUSTOM_COLUMN_LIMIT = 5;

    private final FileParser fileParser;
    private final UnArchiver unArchiver;

    public CommonFileParserImpl() {
        fileParser = new FileParserImpl();
        unArchiver = new UnArchiverImpl();
    }

    @Override
    public List<FullTableModel> parseFile(File file) {
        return parseFile(file, CUSTOM_COLUMN_LIMIT);
    }

    @Override
    public List<FullTableModel> parseFile(File file, long limit) {
        List<File> files = unArchiver.unArchiveFiles(file);

        List<FullTableModel> fullTableModels = fileParser.getFullTable(files, limit);

        unArchiver.deleteFiles(files);
        return fullTableModels;
    }

    @Override
    public FullTableModel parseFile(File file, long limit, String fileName) {
        File targetFile = unArchiver.unArchiveFile(file, fileName);
        FullTableModel fullTableModel = fileParser.getFullTable(targetFile, limit);

        unArchiver.deleteFile(targetFile);
        return fullTableModel;
    }

    @Override
    public List<String> getFileNames(File file) {
        List<File> files = unArchiver.unArchiveFiles(file);

        List<String> names = files
                .stream().map(File::getName)
                .collect(Collectors.toList());

        unArchiver.deleteFiles(files);

        return names;
    }

}