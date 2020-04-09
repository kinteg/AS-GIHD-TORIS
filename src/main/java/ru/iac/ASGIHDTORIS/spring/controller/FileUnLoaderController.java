package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/fileUnLoader")
@Slf4j
public class FileUnLoaderController {

    private final PatternFileRepo patternFileRepo;
    private final PatternTableFileRepo patternTableFileRepo;

    public FileUnLoaderController(PatternFileRepo patternFileRepo, PatternTableFileRepo patternTableFileRepo) {
        this.patternFileRepo = patternFileRepo;
        this.patternTableFileRepo = patternTableFileRepo;
    }

    @Cacheable(cacheNames = "findPatternFileById")
    @GetMapping("/findPatternFileById/{id}")
    public PatternFile findPatternFileById(@PathVariable Long id) {
        return patternFileRepo.findById((long) id);
    }

    @GetMapping("/getAllPatternFileByPatternId")
    public Page<PatternFile> getAllPatternFile(@PageableDefault(sort = "id") Pageable pageable) {
        return patternFileRepo.findAll(pageable);
    }

    @Cacheable(cacheNames = "getAllPatternFileByPatternId")
    @GetMapping("/getAllPatternFileByPatternId/{patternId}")
    public List<PatternFile> getAllPatternFileByPatternId(@PathVariable Long patternId) {
        if (patternId == null || patternId < 0 || patternFileRepo.existsByPatternId(patternId)) {
            return Collections.emptyList();
        }

        return patternFileRepo.findAllByPatternId(patternId);
    }

    @Cacheable(cacheNames = "findPatternTableFileById")
    @GetMapping("/findPatternTableFileById/{id}")
    public PatternTableFile findPatternTableFileById(@PathVariable Long id) {
        return patternTableFileRepo.findById((long) id);
    }

    @Cacheable(cacheNames = "getAllPatternTableFileByPatternId")
    @GetMapping("/getAllPatternTableFileByPatternId")
    public Page<PatternTableFile> getAllPatternTableFile(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableFileRepo.findAll(pageable);
    }

    @Cacheable(cacheNames = "getAllPatternFileByPatternId")
    @GetMapping("/getAllPatternFileByPatternId/{patternTableId}")
    public List<PatternTableFile> getAllPatternTableFileByPatternId(@PathVariable Long patternTableId) {
        if (patternTableId == null || patternTableId < 0 || patternTableFileRepo.existsByPatternTableId(patternTableId)) {
            return Collections.emptyList();
        }

        return patternTableFileRepo.findAllByPatternTableId(patternTableId);
    }

}
