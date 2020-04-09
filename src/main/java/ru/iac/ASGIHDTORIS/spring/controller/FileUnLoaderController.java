package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;
import ru.iac.ASGIHDTORIS.spring.repo.PatternFileRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableFileRepo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

@RequestMapping("api/fileUnLoader")
@RestController
public class FileUnLoaderController {

    @Value("${upload.path.pattern}")
    private String uploadPathPattern;
    @Value("${upload.path.pattern.table}")
    private String uploadPathPatternTable;

    private final PatternFileRepo patternFileRepo;
    private final PatternTableFileRepo patternTableFileRepo;

    public FileUnLoaderController(PatternFileRepo patternFileRepo, PatternTableFileRepo patternTableFileRepo) {
        this.patternFileRepo = patternFileRepo;
        this.patternTableFileRepo = patternTableFileRepo;
    }

    @GetMapping("/findPatternFileById/{id}")
    @Cacheable(cacheNames = "findPatternFileById")
    public PatternFile findPatternFileById(@PathVariable Long id) {
        return patternFileRepo.findById((long) id);
    }

    @GetMapping("/getAllPatternFile")
    public Page<PatternFile> getAllPatternFile(@PageableDefault(sort = "id") Pageable pageable) {
        return patternFileRepo.findAll(pageable);
    }

    @GetMapping("/getAllPatternFileByPatternId/{patternId}")
    @Cacheable(cacheNames = "getAllPatternFileByPatternId")
    public List<PatternFile> getAllPatternFileByPatternId(@PathVariable Long patternId) {
        if (patternId == null || patternId < 0 || !patternFileRepo.existsByPatternId(patternId)) {
            return Collections.emptyList();
        }

        return patternFileRepo.findAllByPatternId(patternId);
    }

    @GetMapping("/findPatternTableFileById/{id}")
    @Cacheable(cacheNames = "findPatternTableFileById")
    public PatternTableFile findPatternTableFileById(@PathVariable Long id) {
        return patternTableFileRepo.findById((long) id);
    }

    @GetMapping("/getAllPatternTable")
    @Cacheable(cacheNames = "getAllPatternTableFileByPatternId")
    public Page<PatternTableFile> getAllPatternTableFile(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableFileRepo.findAll(pageable);
    }

    @GetMapping("/getAllPatternTableFileByPatternId/{patternTableId}")
    @Cacheable(cacheNames = "getAllPatternFileByPatternId")
    public List<PatternTableFile> getAllPatternTableFileByPatternId(@PathVariable Long patternTableId) {
        if (patternTableId == null || patternTableId < 0 || !patternTableFileRepo.existsByPatternTableId(patternTableId)) {
            return Collections.emptyList();
        }

        return patternTableFileRepo.findAllByPatternTableId(patternTableId);
    }

    @GetMapping(value = "/getPatternFile/{file_name}")
    public void getPatternFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        Path file = Paths.get(uploadPathPattern, fileName);
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

    @GetMapping(value = "/getPatternTableFile/{file_name}")
    public void getPatternTableFile(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        Path file = Paths.get(uploadPathPatternTable, fileName);
        if (Files.exists(file)){
            response.setHeader("Content-disposition", "attachment;filename=" + fileName);

            try {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } catch (IOException e) {
                throw new RuntimeException("IOError writing file to output stream");
            }
        }
    }

}
