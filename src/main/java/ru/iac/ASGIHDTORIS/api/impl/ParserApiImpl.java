package ru.iac.ASGIHDTORIS.api.impl;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.ParserApi;
import ru.iac.ASGIHDTORIS.api.factory.impl.ParserFactoryImpl;
import ru.iac.ASGIHDTORIS.parser.Parser;
import ru.iac.ASGIHDTORIS.parser.zip.ZipParser;
import ru.iac.ASGIHDTORIS.parser.zip.ZipParserImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ParserApiImpl implements ParserApi {

    @Override
    public JSONObject getFromFile(File file, long limit) throws IOException, CsvValidationException {

        if (isMultiFiles(file.getName())) {
            ZipParser zipParser = new ZipParserImpl();
            return getFormFiles(zipParser.getFiles(file), limit);
        }


        return getFormFiles(new ArrayList<>(Collections.singletonList(file)), limit);
    }

    @Override
    public JSONObject getFormFiles(List<File> files, long limit) throws IOException, CsvValidationException {

        JSONObject object = new JSONObject();
        JSONArray array = new JSONArray();

        for (File file:
             files) {
            Parser parser = ParserFactoryImpl.getParser(file.getName());

            if (parser != null) {
                array.add(parser.getJSON(file, limit));
            }
        }

        object.put("content", array);

        return object;
    }

    private boolean isMultiFiles(String filename) {
        String fileType = FilenameUtils.getExtension(filename);

        return
                fileType.equals("zip")
                || fileType.equals("rar");
    }

}
