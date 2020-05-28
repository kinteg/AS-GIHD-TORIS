package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo2;
import ru.iac.ASGIHDTORIS.spring.service.source.SourceService;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;
@Slf4j
@RequestMapping("api/source/")
@RestController
public class SourceController {

    private final SourceRepo sourceRepo;
    private final SourceRepo2 sourceRepo2;
    private final SourceService sourceService;
    private final UserService userService;
    private final UserController userController;

    public SourceController(
            SourceRepo sourceRepo,
            SourceRepo2 sourceRepo2,
            SourceService sourceService, UserService userService, UserController userController) {

        this.sourceRepo = sourceRepo;
        this.sourceRepo2 = sourceRepo2;
        this.sourceService = sourceService;
        this.userService = userService;
        this.userController = userController;
    }

    @PostMapping("/create")
    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            allEntries = true)
    public Source createSource(@ModelAttribute Source source, String token) {
        return sourceService.createSource(source, userService.loginUser(token));
    }

    @GetMapping("/checkName")
    @Cacheable(cacheNames = "checkSourceName")
    public boolean checkName(String name) {
        return sourceRepo.existsByShortName(name);
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "getBySourceId")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    @Cacheable(cacheNames = "getAllSource")
    public Page<Source> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Source> getAllSort(
            @ModelAttribute SourceModel source,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {

        source.setHelpModel(helpModel);

        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllArchive")
    @Cacheable(cacheNames = "getAllSourceArchive")
    public Page<Source> getAllArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Source> getAllArchiveSort(
            @ModelAttribute SourceModel source,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {

        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(true);

        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllNotArchive")
    @Cacheable(cacheNames = "getAllSourceNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Source> getAllNotArchiveSort(
            @PageableDefault(sort = "id") SourceModel source,
            @PageableDefault Pageable pageable,
            @ModelAttribute HelpModel helpModel) {

        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(false);

        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/archive/{id}/{token}")
    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    public Source archiveSource(@PathVariable Long id, @PathVariable String token) {
        if (userController.isChangeSource(token, id)) {
            return sourceService.archiveSource(id, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/deArchive/{id}/{token}")
    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    public Source deArchiveSource(@PathVariable Long id, @PathVariable String token) {
        if (userController.isChangeSource(token, id)) {
            return sourceService.deArchiveSource(id, userService.loginUser(token));
        }
        return null;
    }

    @PostMapping("/update")
    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    public Source updateSource(@ModelAttribute Source source, String token) {
        if (userController.isChangeSource(token, source.getId())) {
            return sourceService.updateSource(source, userService.loginUser(token));
        }
        return null;
    }

    @GetMapping("/isArchive/{id}")
    public boolean isArchive(@PathVariable long id) {
        return sourceRepo.isArchive(id);
    }

}
