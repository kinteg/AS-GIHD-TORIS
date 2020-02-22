package ru.iac.ASGIHDTORIS.service.impl;

import com.jayway.jsonpath.JsonPath;
import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.service.ParserService;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@Service
public class ParserServiceImpl implements ParserService {

    private final Parser parserApi;
    private final DataSource dataSource;

    public ParserServiceImpl(Parser parserApi, DataSource dataSource) {
        this.parserApi = parserApi;
        this.dataSource = dataSource;
    }

    @Override
    public String getWithParser(MultipartFile multipartFile, long limit) throws IOException, CsvValidationException {
        File file = FileConverter.multipartIntoFile(multipartFile);

        JSONObject fromFile = parserApi.getFromFile(file, limit);

        return fromFile.toJSONString();

    }

    public String parseIntoBd(MultipartFile multipartFile, String tableInfo) throws IOException, CsvValidationException, SQLException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        String nameFile = JsonPath.read(tableInfo, "$.content.nameFile");

        String table = parserApi.getFromFile(file, nameFile).toJSONString();

//        ParseJsonInBd parseJsonInBd = new ParseJsonInBd(dataSource.getConnection());

//        boolean status = parseJsonInBd.push(table, tableInfo);

        return true ? "ok" : "error";

    }


}
