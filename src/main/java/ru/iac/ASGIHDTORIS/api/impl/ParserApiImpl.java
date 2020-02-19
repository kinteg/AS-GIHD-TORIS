package ru.iac.ASGIHDTORIS.api.impl;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import ru.iac.ASGIHDTORIS.api.Parser;
import ru.iac.ASGIHDTORIS.api.TargetFiles;
import ru.iac.ASGIHDTORIS.api.factory.impl.ParserFactoryImpl;
import ru.iac.ASGIHDTORIS.api.parser.zip.ZipParserImpl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ParserApiImpl implements Parser {

    private final TargetFiles targetFiles;

    public ParserApiImpl() {
        targetFiles = new TargetFiles();
    }

    @Override
    public JSONObject getFromFile(File file, long limit) throws IOException, CsvValidationException {
        List<File> files = targetFiles.isArchive(file.getName()) ?
                new ZipParserImpl().getFiles(file)
                : Collections.singletonList(file);

        return getFormFiles(files, limit);
    }

    @Override
    public JSONObject getFromFile(File file, String fileName) throws IOException, CsvValidationException {
        List<File> files = Collections.singletonList(
                targetFiles.isArchive(file.getName()) ?
                new ZipParserImpl().findByFileName(file, fileName)
                : file
        );

        return getFormFiles(files, -1);
    }

    @Override
    public JSONObject getFormFiles(List<File> files, long limit) throws IOException, CsvValidationException {
        return createJSON(files, limit);
    }

    private JSONObject createJSON(List<File> files, long limit) throws IOException, CsvValidationException {
        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (File file :
                files) {
            ru.iac.ASGIHDTORIS.api.parser.Parser parser = ParserFactoryImpl.getParser(file.getName());

            if (parser != null) {
                array.add(parser.getJSON(file, limit));
            }

            file.delete();
        }

        object.put("content", array);

        return object;
    }

}
