package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.service.sender.DbService;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api")
@Slf4j
public class SendDataDbController {

    private final DbService dbService;

    public SendDataDbController(DbService dbService) {
        this.dbService = dbService;
    }

    @PostMapping("/sendData")
    @ResponseBody
    public String sendData(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "json") String json) throws IOException, SQLException {

        long start = System.currentTimeMillis();
        log.error(json);
        String status = dbService.sendData(multipartFile, json);
        log.info(status);
        long end = System.currentTimeMillis() - start;
        log.info("time: " + end);

        return status;
    }
}