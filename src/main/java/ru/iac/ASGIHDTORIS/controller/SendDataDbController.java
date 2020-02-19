package ru.iac.ASGIHDTORIS.controller;

import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.service.ParserService;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@Slf4j
public class SendDataDbController {

    private final ParserService parserService;

    public SendDataDbController(ParserService parserService) {
        this.parserService = parserService;
    }

    @PostMapping("/sendData")
    @ResponseBody
    public String sendData(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "json") String json) throws IOException, CsvValidationException, SQLException {

        long start = System.currentTimeMillis();
        String status = parserService.parseIntoBd(multipartFile, json);
        log.info(status);
        long end = System.currentTimeMillis() - start;
        log.info("time: " + end);

        return status;
    }
}