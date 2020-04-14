package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.service.dataSender.DataSenderService;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.parser.FirstParserService;
import ru.iac.ASGIHDTORIS.spring.service.parser.ParserService;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RequestMapping("api/fileLoader")
@RestController
public class FileLoaderController {

    private final String DEFAULT_LIMIT = "5";

    private final FirstParserService firstParserService;
    private final ParserService parserService;
    private final FileService fileService;
    private final DataSenderService dataSenderService;

    public FileLoaderController(FirstParserService firstParserService, ParserService parserService, FileService fileService, DataSenderService dataSenderService) {
        this.firstParserService = firstParserService;
        this.parserService = parserService;
        this.fileService = fileService;
        this.dataSenderService = dataSenderService;
    }

    @PostMapping("/check")
    public List<FullTableModel> uploadFile(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit,
            @RequestParam(value = "sourceId", required = false, defaultValue = "")
                    Long patternId) {

        if (multipartFile == null || patternId == null) {
            return Collections.emptyList();
        } else {
            File file = fileService.convertFile(multipartFile);
            return file != null ? parserService.getFullTable(file, limit, patternId) : Collections.emptyList();
        }

    }

    @PostMapping("/firstUpload")
    public List<FullTableModel> uploadFirstFile(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit) {

        if (multipartFile == null) {
            return Collections.emptyList();
        } else {
            return firstParserService.getFullTable(multipartFile, limit);
        }

    }

    @PostMapping("/sendData")
    @CacheEvict(value = {
            "findPatternFileById", "getAllPatternFileByPatternId",
            "getAllPatternFileByPatternId", "findPatternTableFileById",
            "getAllPatternTableFileByPatternId", "getAllPatternFileByPatternId"},
            allEntries = true)
    public boolean sendData(
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam(value = "patternTableId", required = false, defaultValue = "") Long id
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
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "patternId", required = false, defaultValue = "")
                    Long id
    ) throws IOException {
        return dataSenderService.sendDates(multipartFile, id);
    }

}
