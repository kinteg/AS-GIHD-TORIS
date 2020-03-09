package ru.iac.ASGIHDTORIS.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.service.parser.file.ParserService;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("api/")
@Slf4j
public class FileLoaderController {

    private final String DEFAULT_LIMIT_VALUE = "15";
    private final ParserService parserService;

    public FileLoaderController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("single-file")
    @ResponseBody
    public String uploadFile(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT_VALUE)
                    Long limit,
            @RequestParam(value = "sourceId", required = false, defaultValue = "")
                    Long sourceId) throws
            Exception {

        if (multipartFile == null && sourceId == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        } else {
//            log.info("хуй " + parserService.getWithParser(multipartFile, limit, sourceId));
            log.info("хуй " + parserService.getWithParser(multipartFile, limit, sourceId));
            return parserService.getWithParser(multipartFile, limit, sourceId);
        }

    }
}