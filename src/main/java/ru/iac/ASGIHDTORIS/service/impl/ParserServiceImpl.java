package ru.iac.ASGIHDTORIS.service.impl;

import com.opencsv.exceptions.CsvValidationException;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.ParserApi;
import ru.iac.ASGIHDTORIS.service.ParserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
public class ParserServiceImpl implements ParserService {

    private final ParserApi parserApi;

    public ParserServiceImpl(ParserApi parserApi) {
        this.parserApi = parserApi;
    }

    @Override
    public String getWithParser(MultipartFile multipartFile, long limit) throws IOException, CsvValidationException {
        File file = multipartIntoFile(multipartFile);

        JSONObject jsonObject = parserApi.getFromFile(file, limit);

        return jsonObject.toJSONString();

    }

    private File multipartIntoFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        file.deleteOnExit();
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();

        return file;
    }
}
