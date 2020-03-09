package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.factory.file.FileParserFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FilesParser implements Parser {

    private final TargetFiles targetFiles;

    public FilesParser() {
        targetFiles = new TargetFiles();
    }

    @Override
    public JSONObject getFromFile(File file, long limit) throws Exception {
        return getFromFile(file, limit, Collections.emptyList());
    }

    @Override
    public JSONObject getFromFile(File file, long limit, List<TableModel> tableModels) throws Exception {
        List<File> files = targetFiles.isArchive(file.getName()) ?
                getParser(file.getName()).getFiles(file)
                : Collections.singletonList(file);

        return getFormFiles(files, limit, tableModels);
    }

    @Override
    public JSONObject getFormFiles(List<File> files, long limit, List<TableModel> tableModels) throws Exception {
        return createJSON(files, limit, tableModels);
    }

    private ArchiveParser getParser(String filename) {
        return ArchiveFactory.getParser(filename);
    }

    private JSONObject createJSON(List<File> files, long limit, List<TableModel> tableModels) throws Exception {
        JSONObject object = new JSONObject();

        object.put("content", createArray(files, limit, tableModels));

        return object;
    }

    private JSONArray createArray(List<File> files, long limit, List<TableModel> tableModels) throws Exception {
        JSONArray array = new JSONArray();

        for (File file : files) {
            String fileName = file.getName();
            FileParser fileParser = FileParserFactory.getParser(fileName);

            List<TableModel> tableModel = createTableModel(tableModels, fileName);

            if (!tableModel.isEmpty()) {
                TableModel model = tableModel.get(0);
                array.add(fileParser.getJSON(file, limit, model.getModels(), model.getTableName()));
            } else {
                array.add(fileParser.getJSON(file, limit));
            }

            file.delete();
        }

        return array;
    }

    private List<TableModel> createTableModel(List<TableModel> tableModels, String fileName) {
        return tableModels.stream().filter(v -> v.getFilename().equals(fileName)).collect(Collectors.toList());
    }

}
