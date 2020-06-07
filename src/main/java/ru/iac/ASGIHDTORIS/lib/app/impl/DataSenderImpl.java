package ru.iac.ASGIHDTORIS.lib.app.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.app.DataSender;
import ru.iac.ASGIHDTORIS.lib.app.UnArchiver;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.FileFactory;
import ru.iac.ASGIHDTORIS.lib.lib.common.factory.impl.FileFactoryImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.DataModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.FileExtension;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFile;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.impl.PostgreSqlColumnExporterRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.sender.SenderRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.sender.impl.PostgreSqlSenderRepoImpl;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DataSenderImpl implements DataSender {

    private final UnArchiver unArchiver;
    private final SenderRepo senderRepo;
    private final ColumnExporterRepo columnExporterRepo;
    private final TargetFile targetFile;
    private final FileFactory fileFactory;

    public DataSenderImpl(Connection connection) {
        unArchiver = new UnArchiverImpl();
        senderRepo = new PostgreSqlSenderRepoImpl(connection);
        columnExporterRepo = new PostgreSqlColumnExporterRepo(connection);
        targetFile = new TargetFileImpl();
        fileFactory = new FileFactoryImpl();
    }

    @Override
    public boolean sendFile(File file, String filename, String tableName) {
        File findFile = unArchiver.unArchiveFile(file, filename);

        if (findFile == null) {
            return false;
        }

        TableModel tableModel = buildTableModel(filename, tableName);
        Reader reader = createReader(findFile);

        boolean result = senderRepo.insert(reader, tableModel);

        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        unArchiver.deleteFile(findFile);

        return result;
    }

    private TableModel buildTableModel(String filename, String tableName) {
        List<DataModel> dataModels = columnExporterRepo.exportDataModel(tableName);

        return TableModel
                .builder()
                .filename(filename)
                .tableName(tableName)
                .models(dataModels)
                .build();
    }

    @Override
    public boolean sendFiles(File file, Map<String, String> names) {
        List<File> files = unArchiver.unArchiveFiles(file);
        List<String> filenames = new ArrayList<>(names.keySet());
        Map<String, Reader> readerMap = filterFiles(files, filenames);
        Map<String, TableModel> tableModelMap = buildTableModels(readerMap, names);

        send(readerMap, tableModelMap);

        unArchiver.deleteFiles(files);

        return true;
    }

    private Map<String, Reader> filterFiles(List<File> files, List<String> filenames) {
        files = files.stream()
                .filter(file -> filenames.contains(file.getName()))
                .collect(Collectors.toList());

        Map<String, Reader> readerMap = new LinkedHashMap<>();

        for (int i = 0; i < filenames.size() && i < files.size(); i++) {
            String filename = filenames.get(filenames.indexOf(files.get(i).getName()));
            readerMap.put(filename, createReader(files.get(i)));
        }

        return readerMap;
    }

    private Map<String, TableModel> buildTableModels(Map<String, Reader> readerMap, Map<String, String> names) {
        Map<String, TableModel> tableModelMap = new LinkedHashMap<>();
        List<String> filenames = new ArrayList<>(readerMap.keySet());

        for (String filename:
                filenames) {

            tableModelMap.put(filename, buildTableModel(filename, names.get(filename)));
        }

        return tableModelMap;
    }

    private void send(Map<String, Reader> readerMap, Map<String, TableModel> tableModelMap) {
        List<String> filenames = new ArrayList<>(readerMap.keySet());

        for (String filename:
             filenames) {

            senderRepo.insert(readerMap.get(filename), tableModelMap.get(filename));
        }
    }

    private Reader createReader(File file) {
        FileExtension extension = targetFile.getExtension(file.getName());

        return fileFactory.getFileReader(extension, file);
    }
}
