package ru.iac.ASGIHDTORIS.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.service.ParserService;

import java.io.IOException;

@RestController
@RequestMapping("api/")
@Slf4j
public class FileLoaderController {

    private final String DEFAULT_LIMIT_VALUE = "2";
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
            @RequestParam(value = "sourceId", required = false, defaultValue = "") String sourceId) throws
            IOException,
            CsvValidationException {

        if (multipartFile == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        } else {
            System.out.println(parserService.getWithParser(multipartFile, limit));
            return parserService.getWithParser(multipartFile, limit);
        }

    }
}