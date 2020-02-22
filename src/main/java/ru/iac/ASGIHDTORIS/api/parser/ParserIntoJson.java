package ru.iac.ASGIHDTORIS.api.parser;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.factory.file.FileParserFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ParserIntoJson implements Parser {

    private final TargetFiles targetFiles;

    public ParserIntoJson() {
        targetFiles = new TargetFiles();
    }

    @Override
    public JSONObject getFromFile(File file, long limit) throws IOException, CsvValidationException {
        List<File> files = targetFiles.isArchive(file.getName()) ?
                getParser(file.getName()).getFiles(file)
                : Collections.singletonList(file);

        return getFormFiles(files, limit);
    }

    @Override
    public JSONObject getFromFile(File file, String fileName) throws IOException, CsvValidationException {
        List<File> files = Collections.singletonList(
                targetFiles.isArchive(file.getName()) ?
                        getParser(file.getName()).findByFileName(file, fileName)
                        : file
        );

        return getFormFiles(files, -1);
    }

    @Override
    public JSONObject getFormFiles(List<File> files, long limit) throws IOException, CsvValidationException {
        return createJSON(files, limit);
    }

    private ArchiveParser getParser(String filename) {
        return ArchiveFactory.getParser(filename);
    }

    private JSONObject createJSON(List<File> files, long limit) throws IOException, CsvValidationException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (File file :
                files) {
            FileParser fileParser = FileParserFactory.getParser(file.getName());

            if (fileParser != null) {
                array.add(fileParser.getJSON(file, limit));
            }

            file.delete();
        }

        object.put("content", array);

        return object;
    }

}
