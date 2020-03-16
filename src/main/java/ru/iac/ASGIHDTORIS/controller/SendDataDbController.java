package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;
import ru.iac.ASGIHDTORIS.service.sender.DbService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            @RequestParam(value = "filename") List<String> nameFile,
            @RequestParam(value = "tableName") List<String> nameTable,
            @RequestParam(value = "id") Long id
            ) throws IOException, SQLException {

        if (multipartFile == null && id == null) {
            return new ResponseEntity<>("Error", HttpStatus.NOT_FOUND).toString();
        } else {
            log.info(nameFile.toString());
            log.info(nameTable.toString());

            List<TableModel> tableModels = new ArrayList<>();

            for (int i = 0; i < nameFile.size() && i < nameTable.size(); i++) {
                TableModel tableModel = new TableModel(nameFile.get(i), nameTable.get(i));
                tableModels.add(tableModel);
            }

            return dbService.sendData(multipartFile, tableModels, id);
        }

    }
}