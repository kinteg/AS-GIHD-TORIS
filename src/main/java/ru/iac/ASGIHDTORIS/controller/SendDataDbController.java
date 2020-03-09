package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            @RequestParam(value = "nameFile") String nameFile,
            @RequestParam(value = "nameTable") String nameTable,
            @RequestParam(value = "id") Long id
            ) throws IOException, SQLException {

        if (multipartFile == null && id == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        } else {
            return dbService.sendData(multipartFile, nameFile, nameTable, id);
        }

    }
}