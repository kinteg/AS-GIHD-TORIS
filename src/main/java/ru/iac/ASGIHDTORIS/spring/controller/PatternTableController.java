package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;
import ru.iac.ASGIHDTORIS.spring.repo.TableRepo;
import ru.iac.ASGIHDTORIS.spring.service.patterTable.PatternTableService;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("api/tableCreator/")
@RestController
public class PatternTableController {

    private final PatternTableRepo patternTableRepo;
    private final PatternTableRepo2 patternTableRepo2;
    private final PatternTableService patternTableService;

    public PatternTableController(
            PatternTableRepo patternTableRepo,
            PatternTableRepo2 patternTableRepo2,
            PatternTableService patternTableService) {

        this.patternTableRepo = patternTableRepo;
        this.patternTableRepo2 = patternTableRepo2;
        this.patternTableService = patternTableService;
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @PostMapping("/create")
    @ResponseBody
    public PatternTableModelStatus createPattern(
            @ModelAttribute TableModel tableModel,
            @ModelAttribute DataModelList dataModelList,
            @RequestParam Long patternId
    ) {
        return patternTableService.createPatternTable(tableModel, dataModelList, patternId);
    }

    @Cacheable(cacheNames = "getTable")
    @PostMapping("/getTable")
    public FullTableModelPage getTable(
            @RequestParam Long id,
            @ModelAttribute SearchModel searchModel,
            @PageableDefault Pageable pageable
    ) {
        return patternTableService.getTable(id, searchModel, pageable);
    }

    @Cacheable(cacheNames = "getByPatternTableId")
    @GetMapping("/{id}")
    public PatternTable getById(@PathVariable Long id) {
        return patternTableRepo.findById((long) id);
    }

    @Cacheable(cacheNames = "getAllPatternTable")
    @GetMapping("/getAll")
    public Page<PatternTable> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<PatternTable> getAllSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableByPatternId")
    @GetMapping("/getAll/{patternId}")
    public Page<PatternTable> getAllByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternId(patternId, pageable);
    }

    @Cacheable(cacheNames = "getAllPatternTableBySourceId")
    @GetMapping("/getAllBySource/{sourceId}")
    public Page<PatternTable> getAllBySourceId(@PageableDefault(sort = "id") Pageable pageable, @PathVariable Long sourceId) {
        return patternTableRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{patternId}")
    public Page<PatternTable> getAllByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        log.info(pattern.toString());
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllBySourceSort/{sourceId}")
    public Page<PatternTable> getAllBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableArchive")
    @GetMapping("/getAllArchive")
    public Page<PatternTable> getAllArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllArchiveSort")
    public Page<PatternTable> getAllArchiveSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableArchiveByPatternId")
    @GetMapping("/getAllArchive/{patternId}")
    public Page<PatternTable> getAllArchiveByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, true, pageable);
    }

    @GetMapping("/getAllArchiveSort/{patternId}")
    public Page<PatternTable> getAllArchiveByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @GetMapping("/getAllArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableNotArchive")
    @GetMapping("/getAllNotArchive")
    public Page<PatternTable> getAllNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort")
    public Page<PatternTable> getAllNotArchiveSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableNotArchiveByPatternId")
    @GetMapping("/getAllNotArchive/{patternId}")
    public Page<PatternTable> getAllNotArchiveByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort/{patternId}")
    public Page<PatternTable> getAllNotArchiveByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableNotArchiveBySourceId")
    @GetMapping("/getAllNotArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/archive/{id}")
    public PatternTable archivePattern(@PathVariable Long id) {
        return patternTableService.archivePatternTable(id);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/deArchive/{id}")
    public PatternTable deArchivePattern(@PathVariable Long id) {
        return patternTableService.deArchivePatternTable(id);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/archivePatterns/{id}")
    public List<PatternTable> archivePatterns(@PathVariable Long id) {
        return patternTableService.archivePatternTablesByPatternId(id);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/deArchivePatterns/{id}")
    public List<PatternTable> deArchivePatterns(@PathVariable Long id) {
        return patternTableService.deArchivePatternTablesByPatternId(id);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/archivePatternsBySource/{id}")
    public List<PatternTable> archivePatternsBySource(@PathVariable Long id) {
        return patternTableService.archivePatternTablesBySourceId(id);
    }

    @CacheEvict(value = {
            "getTable", "getByPatternTableId",
            "getAllPatternTable",
            "getAllPatternTableByPatternId",
            "getAllPatternTableBySourceId",
            "getAllPatternTableArchive",
            "getAllPatternTableArchiveByPatternId",
            "getAllPatternTableArchiveBySourceId",
            "getAllPatternTableNotArchive",
            "getAllPatternTableNotArchiveByPatternId",
            "getAllPatternTableNotArchiveBySourceId",
            "existByPatternTableName"},
            allEntries = true)
    @GetMapping("/deArchivePatternTablesBySource/{id}")
    public List<PatternTable> deArchivePatternsBySource(@PathVariable Long id) {
        return patternTableService.deArchivePatternTablesBySourceId(id);
    }

    @GetMapping("exist/{name}")
    public boolean existByName(@PathVariable String name) {
        return patternTableRepo.existsByNameTable(name);
    }

}
