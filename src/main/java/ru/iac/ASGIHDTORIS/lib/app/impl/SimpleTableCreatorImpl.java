package ru.iac.ASGIHDTORIS.lib.app.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.app.SimpleTableCreator;
import ru.iac.ASGIHDTORIS.lib.app.UnArchiver;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModelStatus;
import ru.iac.ASGIHDTORIS.lib.lib.db.creator.CreatorRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.creator.impl.PostgresqlCreatorRepo;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.SimpleFileParser;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser.impl.SimpleFileParserImpl;

import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SimpleTableCreatorImpl implements SimpleTableCreator {

    private final SimpleFileParser simpleFileParser;
    private final CreatorRepo creatorRepo;
    private final UnArchiver unArchiver;

    public SimpleTableCreatorImpl(Connection connection) {
        simpleFileParser = new SimpleFileParserImpl();
        creatorRepo = new PostgresqlCreatorRepo(connection);
        unArchiver = new UnArchiverImpl();
    }

    @Override
    public List<TableModelStatus> createTable(File file) {
        List<File> files = unArchiver.unArchiveFiles(file);
        List<TableModel> tableModels = simpleFileParser.getFullTable(files);

        return createTable(tableModels);
    }

    @Override
    public List<TableModelStatus> createTable(List<TableModel> tableModel) {
        return tableModel.stream().map(this::createTable).collect(Collectors.toList());
    }

    @Override
    public TableModelStatus createTable(TableModel tableModel) {
        System.out.println(tableModel);
        return creatorRepo.createTable(tableModel);
    }

}
