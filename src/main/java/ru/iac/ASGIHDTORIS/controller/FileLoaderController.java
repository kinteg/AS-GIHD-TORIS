package ru.iac.ASGIHDTORIS.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.parser.csv.SimpleCsvParser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("api/")
@Slf4j
public class FileLoaderController {

    @PostMapping("single-file")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) throws IOException, CsvValidationException {

        if (multipartFile == null)
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        else {
            //TODO временный код для проверки работоспособности
            SimpleCsvParser simpleCsvParser = new SimpleCsvParser();
            File file = new File(multipartFile.getOriginalFilename());
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(multipartFile.getBytes());
            fileOutputStream.close();
            log.info(simpleCsvParser.getJSON(file).toJSONString());
            return "OK";
        }

    }
}
