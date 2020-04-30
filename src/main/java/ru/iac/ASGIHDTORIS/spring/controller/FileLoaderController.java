package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.service.dataChecker.DataCheckerService;
import ru.iac.ASGIHDTORIS.spring.service.dataSender.DataSenderService;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.parser.FileParserService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("api/fileLoader")
@RestController
public class FileLoaderController {

    private final String DEFAULT_LIMIT = "5";

    private final FileParserService fileParserService;
    private final FileService fileService;
    private final DataSenderService dataSenderService;
    private final DataCheckerService dataCheckerService;

    public FileLoaderController(FileParserService fileParserService, FileService fileService, DataSenderService dataSenderService, DataCheckerService dataCheckerService) {
        this.fileParserService = fileParserService;
        this.fileService = fileService;
        this.dataSenderService = dataSenderService;
        this.dataCheckerService = dataCheckerService;
    }

    @PostMapping("/check")
    public List<FullTableModel> uploadFile(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit,
            @RequestParam(value = "sourceId", defaultValue = "")
                    Long patternId) {

        File file = fileService.convertFile(multipartFile);
        return fileParserService.getFullTable(file, limit, patternId);
    }

    @PostMapping("/firstUpload")
    public List<FullTableModel> uploadFirstFile(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit) {


        File file = fileService.convertFile(multipartFile);
        return fileParserService.getFullTable(file, limit);
    }

    @PostMapping("/update")
    public FullTableModel uploadUpdateFile(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit,
            @RequestParam(value = "patternTableName")
                    String patternTableName,
            @RequestParam(value = "patternNameFile")
                    String patternNameFile) {

        File file = fileService.convertFile(multipartFile);
        return fileParserService.getFullTable(file, limit, patternTableName, patternNameFile);
    }

    @PostMapping("/sendData")
    @CacheEvict(value = {
            "findPatternFileById", "getAllPatternFileByPatternId",
            "getAllPatternFileByPatternId", "findPatternTableFileById",
            "getAllPatternTableFileByPatternId", "getAllPatternFileByPatternId"},
            allEntries = true)
    public boolean sendData(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "patternTableId", defaultValue = "") Long id
    ) throws IOException {
        return dataSenderService.sendData(multipartFile, id);
    }

    @PostMapping("/sendDates")
    @CacheEvict(value = {
            "findPatternFileById", "getAllPatternFileByPatternId",
            "getAllPatternFileByPatternId", "findPatternTableFileById",
            "getAllPatternTableFileByPatternId", "getAllPatternFileByPatternId"},
            allEntries = true)
    public boolean sendDates(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "patternId", defaultValue = "")
                    Long id
    ) throws IOException {
        return dataSenderService.sendDates(multipartFile, id);
    }

    @PostMapping("/checkData")
    public FileStatusModel checkData(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "patternTableId") Long id
    ) throws Exception {
        File file = fileService.convertFile(multipartFile);
        return dataCheckerService.checkData(file, id);
    }

    @PostMapping("/checkDates")
    public List<FileStatusModel> checkDates(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "patternId")
                    Long id
    ) throws Exception {
        File file = fileService.convertFile(multipartFile);
        return dataCheckerService.checkDates(file, id);
    }

}
