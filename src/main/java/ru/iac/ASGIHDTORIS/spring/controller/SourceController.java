package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
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

import java.time.LocalDateTime;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final SourceRepo2 sourceRepo2;
    private final SourceService sourceService;

    public SourceController(
            SourceRepo sourceRepo,
            SourceRepo2 sourceRepo2, SourceService sourceService) {
        this.sourceRepo = sourceRepo;
        this.sourceRepo2 = sourceRepo2;
        this.sourceService = sourceService;
    }

    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    @PostMapping("/create")
    @ResponseBody
    public Source createSource(@ModelAttribute Source source) {
        return sourceService.createSource(source);
    }

    @Cacheable(cacheNames = "checkSourceName")
    @GetMapping("/checkName")
    public boolean checkName(String name) {
        return sourceRepo.existsByShortName(name);
    }

    @Cacheable(cacheNames = "getBySourceId")
    @GetMapping("/{id}")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long) id);
    }

    @Cacheable(cacheNames = "getAllSource")
    @GetMapping("/getAll")
    public Page<Source> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Source> getAllSort(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @Cacheable(cacheNames = "getAllSourceArchive")
    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Source> getAllArchiveSort(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(true);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @Cacheable(cacheNames = "getAllSourceNotArchive")
    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Source> getAllNotArchiveSort(@PageableDefault(sort = "id") SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(false);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable Long id) {
        return sourceService.archiveSource(id);
    }

    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable Long id) {
        return sourceService.deArchiveSource(id);
    }

    @CacheEvict(value = {
            "checkSourceName", "getBySourceId",
            "getAllSource", "getAllSourceArchive",
            "getAllSourceNotArchive"},
            beforeInvocation = true, allEntries = true)
    @PostMapping("/update")
    @ResponseBody
    public Source updateSource(@ModelAttribute Source source) {
        return sourceService.updateSource(source);
    }

}
