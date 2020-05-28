package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;
import ru.iac.ASGIHDTORIS.spring.service.pattern.PatternService;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;

import java.util.List;

@RequestMapping("api/pattern/")
@RestController
public class PatternController {

    private final PatternRepo patternRepo;
    private final PatternRepo2 patternRepo2;
    private final PatternService patternService;
    private final UserController userController;
    private final UserService userService;

    public PatternController(
            PatternRepo patternRepo,
            PatternRepo2 patternRepo2,
            PatternService patternService,
            UserController userController, UserService userService) {
        this.patternRepo = patternRepo;
        this.patternRepo2 = patternRepo2;
        this.patternService = patternService;
        this.userController = userController;
        this.userService = userService;
    }

    @PostMapping("/create")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public Pattern createPattern(@ModelAttribute Pattern pattern, String token) {
        if (userController.isChangeSource(token, pattern.getSourceId())) {
            return patternService.createPattern(pattern, userService.loginUser(token));
        }

        return null;
    }

    @GetMapping("/{id}")
    public Pattern getPatternById(@PathVariable Long id) {
        return patternRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    @Cacheable(cacheNames = "getAllPattern")
    public Page<Pattern> getAllPattern(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Pattern> getAllPatternSort(
            @ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAll/{sourceId}")
    @Cacheable(cacheNames = "getAllPatternBySourceId")
    public Page<Pattern> getAllPatternBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{sourceId}")
    public Page<Pattern> getAllPatternBySourceIdSort(
            @ModelAttribute PatternModel pattern,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive")
    @Cacheable(cacheNames = "getAllPatternArchive")
    public Page<Pattern> getAllPatternArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Pattern> getAllPatternArchiveSort(
            @ModelAttribute PatternModel pattern,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive/{sourceId}")
    @Cacheable(cacheNames = "getAllPatternArchiveBySourceId")
    public Page<Pattern> getAllPatternArchiveBySourceId(
            @PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @PostMapping("/getAllArchiveSort/{sourceId}")
    public Page<Pattern> getAllPatternArchiveBySourceIdSort(
            @ModelAttribute PatternModel pattern,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive")
    @Cacheable(cacheNames = "getAllPatternNotArchive")
    public Page<Pattern> getAllPatternNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Pattern> getAllPatternNotArchiveSort(
            @ModelAttribute PatternModel pattern,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive/{sourceId}")
    @Cacheable(cacheNames = "getAllPatternNotArchiveBySourceId")
    public Page<Pattern> getAllPatternNotArchiveBySourceId(
            @PathVariable Long sourceId,
            @PageableDefault(sort = "id") Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort/{sourceId}")
    public Page<Pattern> getAllPatternNotArchiveBuSourceIdSort(
            @ModelAttribute PatternModel pattern,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/archive/{id}/{token}")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public Pattern archivePattern(@PathVariable Long id, @PathVariable String token) {
        if (userController.isChangeSource(token, patternRepo.findById((long) id).getSourceId())) {
            return patternService.archivePattern(id, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/deArchive/{id}/{token}")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public Pattern deArchivePattern(@PathVariable Long id, @PathVariable String token) {
        if (userController.isChangeSource(token, patternRepo.findById((long) id).getSourceId())) {
            return patternService.deArchivePattern(id, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/archivePatterns/{sourceId}/{token}")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public List<Pattern> archivePatterns(@PathVariable Long sourceId, @PathVariable String token) {
        if (userController.isChangeSource(token, sourceId)) {
            return patternService.archivePatterns(sourceId, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/deArchivePatterns/{sourceId}/{token}")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public List<Pattern> deArchivePatterns(@PathVariable Long sourceId, @PathVariable String token) {
        if (userController.isChangeSource(token, sourceId)) {
            return patternService.deArchivePatterns(sourceId, userService.loginUser(token));
        }
        return null;
    }

    @PostMapping("/update")
    @CacheEvict(value = {
            "getPatternById", "getAllPattern",
            "getAllPatternBySourceId",
            "getAllPatternArchive",
            "getAllPatternArchiveBySourceId",
            "getAllPatternNotArchive",
            "getAllPatternNotArchiveBySourceId"},
            allEntries = true)
    public Pattern update(@ModelAttribute Pattern pattern, String token) {
        if (userController.isChangeSource(token, pattern.getSourceId())) {
            return patternService.updatePattern(pattern, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/isArchive/{id}")
    public boolean isArchive(@PathVariable long id) {
        return patternRepo.isArchive(id);
    }

}
