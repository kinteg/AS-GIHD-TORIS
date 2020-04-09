package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.service.pattern.PatternService;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;

import java.util.List;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class PatternController {

    private final PatternRepo patternRepo;
    private final PatternRepo2 patternRepo2;
    private final PatternService patternService;

    public PatternController(
            PatternRepo patternRepo,
            PatternRepo2 patternRepo2,
            PatternService patternService) {

        this.patternRepo = patternRepo;
        this.patternRepo2 = patternRepo2;
        this.patternService = patternService;
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
                    "getAllPatternBySourceId",
                    "getAllPatternArchive",
                    "getAllPatternArchiveBySourceId",
                    "getAllPatternNotArchive",
                    "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @PostMapping("/create")
    public Pattern createPattern(@ModelAttribute Pattern pattern) {
        return patternService.createPattern(pattern);
    }

    @Cacheable(cacheNames = "getPatternById")
    @GetMapping("/{id}")
    public Pattern getPatternById(@PathVariable Long id) {
        return patternRepo.findById((long) id);
    }

    @Cacheable(cacheNames = "getAllPattern")
    @GetMapping("/getAll")
    public Page<Pattern> getAllPattern(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Pattern> getAllPatternSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternBySourceId")
    @GetMapping("/getAll/{sourceId}")
    public Page<Pattern> getAllPatternBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{sourceId}")
    public Page<Pattern> getAllPatternBySourceIdSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternArchive")
    @GetMapping("/getAllArchive")
    public Page<Pattern> getAllPatternArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Pattern> getAllPatternArchiveSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternArchiveBySourceId")
    @GetMapping("/getAllArchive/{sourceId}")
    public Page<Pattern> getAllPatternArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @PostMapping("/getAllArchiveSort/{sourceId}")
    public Page<Pattern> getAllPatternArchiveBySourceIdSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternNotArchive")
    @GetMapping("/getAllNotArchive")
    public Page<Pattern> getAllPatternNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Pattern> getAllPatternNotArchiveSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternNotArchiveBySourceId")
    @GetMapping("/getAllNotArchive/{sourceId}")
    public Page<Pattern> getAllPatternNotArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort/{sourceId}")
    public Page<Pattern> getAllPatternNotArchiveBuSourceIdSort(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @GetMapping("/archive/{id}")
    public Pattern archivePattern(@PathVariable Long id) {
        return patternService.archivePattern(id);
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @GetMapping("/deArchive/{id}")
    public Pattern deArchivePattern(@PathVariable Long id) {
        return patternService.deArchivePattern(id);
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @GetMapping("/archivePatterns/{sourceId}")
    public List<Pattern> archivePatterns(@PathVariable Long sourceId) {
        return patternService.archivePatterns(sourceId);
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @GetMapping("/deArchivePatterns/{sourceId}")
    public List<Pattern> deArchivePatterns(@PathVariable Long sourceId) {
        return patternService.deArchivePatterns(sourceId);
    }

    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    @PostMapping("/update")
    public Pattern update(@ModelAttribute Pattern pattern) {
        return patternService.updatePattern(pattern);
    }

}
