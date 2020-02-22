package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.service.DBService;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@Slf4j
public class SendDataDbController {

    private final DBService dbService;

    public SendDataDbController(DBService dbService) {
        this.dbService = dbService;
    }

    @PostMapping("/sendData")
    @ResponseBody
    public String sendData(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "json") String json) throws IOException, SQLException {

        long start = System.currentTimeMillis();
        String status = dbService.sendData(multipartFile, json);
        log.info(status);
        long end = System.currentTimeMillis() - start;
        log.info("time: " + end);

        return status;
    }
}