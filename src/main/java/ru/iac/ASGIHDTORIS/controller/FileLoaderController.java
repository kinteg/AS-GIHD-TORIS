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

    private final ParserService parserService;

    public FileLoaderController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("single-file")
    @ResponseBody
    public String uploadFile(@RequestParam(value = "file", required = false) MultipartFile multipartFile) throws IOException, CsvValidationException {

        if (multipartFile == null)
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        else {
            log.info(parserService.getWithParser(multipartFile, 2));
            return parserService.getWithParser(multipartFile, 2);
        }
    }
}