package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.parser.FirstParserService;
import ru.iac.ASGIHDTORIS.spring.service.parser.ParserService;
import ru.iac.ASGIHDTORIS.spring.service.sender.FileSenderService;

import java.io.File;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/fileLoader")
@Slf4j
public class FileLoaderController {

    private final String DEFAULT_LIMIT = "15";

    private final FirstParserService firstParserService;
    private final ParserService parserService;
    private final FileService fileService;
    private final FileSenderService fileSenderService;
    private final PatternTableRepo patternTableRepo;

    public FileLoaderController(FirstParserService firstParserService, ParserService parserService, FileService fileService, FileSenderService fileSenderService, PatternTableRepo patternTableRepo) {
        this.firstParserService = firstParserService;
        this.parserService = parserService;
        this.fileService = fileService;
        this.fileSenderService = fileSenderService;
        this.patternTableRepo = patternTableRepo;
    }

    @PostMapping("/check")
    @ResponseBody
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
    @ResponseBody
    public List<FullTableModel> uploadFile(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit) {

        if (multipartFile == null) {
            return Collections.emptyList();
        } else {
            log.info(multipartFile.getName());
            File file = fileService.convertFile(multipartFile);
            log.info(file.getName());
            return firstParserService.getFullTable(file, limit);
        }

    }

    @PostMapping("/sendData")
    public boolean sendData(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "name", required = false, defaultValue = "")
                    Long id) {

        if (multipartFile == null || id == null || id < 0 || !patternTableRepo.existsById(id)) {
            return false;
        } else {
            PatternTable patternTable = patternTableRepo.findById((long)id);
            File file = fileService.convertFile(multipartFile);

            return file != null && fileSenderService.sendFile(patternTable, file);
        }

    }
}
