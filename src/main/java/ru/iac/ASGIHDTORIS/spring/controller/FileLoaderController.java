package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.service.parser.FirstParserService;
import ru.iac.ASGIHDTORIS.spring.service.parser.ParserService;

import java.util.List;

@RestController
@RequestMapping("api/")
@Slf4j
public class FileLoaderController {

    private final String DEFAULT_LIMIT = "15";
    private final FirstParserService firstParserService;
    private final ParserService parserService;

    public FileLoaderController(FirstParserService firstParserService, ParserService parserService) {
        this.firstParserService = firstParserService;
        this.parserService = parserService;
    }

    @PostMapping("/check")
    @ResponseBody
    public List<FullTableModel> uploadFile(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit,
            @RequestParam(value = "patternId", required = false, defaultValue = "")
                    Long patternId) {

        if (multipartFile == null && patternId == null) {
            return null;
        } else {
            return parserService.getFullTable(multipartFile, limit, patternId);
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
            return null;
        } else {
            return firstParserService.getFullTable(multipartFile, limit);
        }

    }

}
