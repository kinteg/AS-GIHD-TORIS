package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;
import ru.iac.ASGIHDTORIS.spring.repo.PatternFileRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableFileRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.parser.FirstParserService;
import ru.iac.ASGIHDTORIS.spring.service.parser.ParserService;
import ru.iac.ASGIHDTORIS.spring.service.sender.FileSenderService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/fileLoader")
@Slf4j
public class FileLoaderController {

    @Value("${upload.path.pattern}")
    private String uploadPathPattern;
    @Value("${upload.path.pattern.table}")
    private String uploadPathPatternTable;

    private final String DEFAULT_LIMIT = "5";

    private final FirstParserService firstParserService;
    private final ParserService parserService;
    private final FileService fileService;
    private final FileSenderService fileSenderService;
    private final PatternTableRepo patternTableRepo;
    private final PatternRepo patternRepo;
    private final PatternFileRepo patternFileRepo;
    private final PatternTableFileRepo patternTableFileRepo;

    public FileLoaderController(FirstParserService firstParserService, ParserService parserService, FileService fileService, FileSenderService fileSenderService, PatternTableRepo patternTableRepo, PatternRepo patternRepo, PatternFileRepo patternFileRepo, PatternTableFileRepo patternTableFileRepo) {
        this.firstParserService = firstParserService;
        this.parserService = parserService;
        this.fileService = fileService;
        this.fileSenderService = fileSenderService;
        this.patternTableRepo = patternTableRepo;
        this.patternRepo = patternRepo;
        this.patternFileRepo = patternFileRepo;
        this.patternTableFileRepo = patternTableFileRepo;
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

    @CacheEvict(value =
            "findPatternFileById, getAllPatternFileByPatternId, " +
                    "getAllPatternFileByPatternId, findPatternTableFileById, " +
                    "getAllPatternTableFileByPatternId, getAllPatternFileByPatternId, ",
            allEntries = true)
    @PostMapping("/sendData")
    public boolean sendData(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "patternTableId", required = false, defaultValue = "")
                    Long id) throws IOException {

        if (multipartFile == null || id == null || id < 0 || !patternTableRepo.existsById(id)) {
            return false;
        } else {
            PatternTable patternTable = patternTableRepo.findById((long) id);
            File file = fileService.convertFile(multipartFile);
            boolean result = fileSenderService.sendFile(patternTable, file);

            uploadPatternTableFiles(multipartFile, id);
            cleanPatternTableFiles(id);

            file.delete();
            return result;
        }

    }

    @CacheEvict(value =
            "findPatternFileById, getAllPatternFileByPatternId, " +
                    "getAllPatternFileByPatternId, findPatternTableFileById, " +
                    "getAllPatternTableFileByPatternId, getAllPatternFileByPatternId, ",
            allEntries = true)
    @PostMapping("/sendDates")
    public boolean sendDates(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "patternId", required = false, defaultValue = "")
                    Long id) throws IOException {

        if (multipartFile == null || id == null || id < 0 || !patternRepo.existsById(id)) {
            return false;
        } else {
            List<PatternTable> patternTables = patternTableRepo.findAllByPatternId(id);
            File file = fileService.convertFile(multipartFile);

            uploadPatternFiles(multipartFile, id);
            cleanPatternFiles(id);

            return file != null && fileSenderService.sendFiles(patternTables, file);
        }

    }

    private void uploadPatternTableFiles(MultipartFile file, Long id) throws IOException {
        String fileName = uploadFile(file, uploadPathPatternTable);

        PatternTableFile patternTableFile = PatternTableFile
                .builder()
                .patternTableId(id)
                .file(fileName)
                .dateCreation(LocalDateTime.now())
                .build();
        patternTableFileRepo.save(patternTableFile);
    }

    private void uploadPatternFiles(MultipartFile file, Long id) throws IOException {
        String fileName = uploadFile(file, uploadPathPattern);;

        PatternFile patternTableFile = PatternFile
                .builder()
                .patternId(id)
                .file(fileName)
                .dateCreation(LocalDateTime.now())
                .build();
        patternFileRepo.save(patternTableFile);
    }

    private String uploadFile(MultipartFile file, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        LocalDateTime dataCreation = LocalDateTime.now();
        String fileName = dataCreation.format(
                DateTimeFormatter.ISO_DATE_TIME) + "." +
                file.getOriginalFilename();
        String fullPath = uploadPath + "/" + fileName;

        file.transferTo(new File(fullPath));

        return fileName;
    }

    private boolean cleanPatternTableFiles(Long id) {
        if (patternTableFileRepo.countAllByPatternTableId(id) > 3) {
            PatternTableFile patternTableFile = patternTableFileRepo
                    .findByPatternTableIdAndDateCreationMin(id);

            File file = new File(
                    uploadPathPatternTable.trim() + "/" +
                            patternTableFile.getFile().trim());

            if (file.exists()) {
                file.delete();
            }

            patternTableFileRepo.delete(patternTableFile);
        }

        return patternTableFileRepo.countAllByPatternTableId(id) <= 3 || cleanPatternTableFiles(id);
    }

    private boolean cleanPatternFiles(Long id) {
        if (patternFileRepo.countAllByPatternId(id) > 3) {
            PatternFile patternFile = patternFileRepo
                    .findByPatternIdAndDateCreationMin(id);

            File file = new File(
                    uploadPathPattern.trim() + "/" +
                            patternFile.getFile().trim());

            if (file.exists()) {
                file.delete();
            }

            patternFileRepo.delete(patternFile);
        }

        return patternFileRepo.countAllByPatternId(id) <= 3 || cleanPatternFiles(id);
    }

}
