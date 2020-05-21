package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;
import ru.iac.ASGIHDTORIS.lib.app.CommonFileParser;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.spring.service.dataChecker.DataCheckerService;
import ru.iac.ASGIHDTORIS.spring.service.dataSender.DataSenderService;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequestMapping("api/fileLoader")
@RestController
public class FileLoaderController {

    private final String DEFAULT_LIMIT = "5";

    private final FileService fileService;
    private final DataSenderService dataSenderService;
    private final DataCheckerService dataCheckerService;
    private final CommonFileParser commonFileParser;

    public FileLoaderController(FileService fileService, DataSenderService dataSenderService, DataCheckerService dataCheckerService, CommonFileParser commonFileParser) {
        this.fileService = fileService;
        this.dataSenderService = dataSenderService;
        this.dataCheckerService = dataCheckerService;
        this.commonFileParser = commonFileParser;
    }

    @PostMapping("/firstUpload")
    public List<FullTableModel> uploadFirstFile(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit) {
        File file = fileService.convertFile(multipartFile);
        List<FullTableModel> fullTableModels = commonFileParser.parseFile(file, limit);
        fileService.delete(file);

        return fullTableModels;
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
        FullTableModel fullTableModel = commonFileParser.parseFile(file, limit, patternNameFile);
        fullTableModel.getTableModel().setTableName(patternTableName);
        fileService.delete(file);

        return fullTableModel;
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
        FileStatusModel fileStatusModel = dataCheckerService.checkData(file, id);
        fileService.delete(file);

        return fileStatusModel;
    }

    @PostMapping("/checkDates")
    public List<FileStatusModel> checkDates(
            @RequestParam(value = "file")
                    MultipartFile multipartFile,
            @RequestParam(value = "patternId")
                    Long id
    ) throws Exception {
        File file = fileService.convertFile(multipartFile);
        List<FileStatusModel> fileStatusModels = dataCheckerService.checkDates(file, id);
        fileService.delete(file);

        return fileStatusModels;
    }

}
