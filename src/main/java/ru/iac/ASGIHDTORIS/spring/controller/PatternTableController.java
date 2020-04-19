package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import ru.iac.ASGIHDTORIS.spring.service.patterTable.PatternTableService;

import java.util.List;
@Slf4j
@RequestMapping("api/tableCreator/")
@RestController
public class PatternTableController {

    private final PatternTableRepo patternTableRepo;
    private final PatternTableRepo2 patternTableRepo2;
    private final PatternTableService patternTableService;

    public PatternTableController(
            PatternTableRepo patternTableRepo,
            PatternTableRepo2 patternTableRepo2,
            PatternTableService patternTableService
    ) {
        this.patternTableRepo = patternTableRepo;
        this.patternTableRepo2 = patternTableRepo2;
        this.patternTableService = patternTableService;
    }

    @PostMapping("/create")
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
    @ResponseBody
    public PatternTableModelStatus createPatternTable(
            @ModelAttribute TableModel tableModel,
            @ModelAttribute DataModelList dataModelList,
            @RequestParam Long patternId
    ) {
        return patternTableService.createPatternTable(tableModel, dataModelList, patternId);
    }

    @PostMapping("/update")
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
    @ResponseBody
    public PatternTableModelStatus updatePatternTable(
            @ModelAttribute TableModel tableModel,
            @ModelAttribute DataModelList dataModelList,
            @RequestParam Long patternTableId
    ) {
        return patternTableService.updatePatternTable(tableModel, dataModelList, patternTableId);
    }

    @PostMapping("/getTable")
    @Cacheable(cacheNames = "getTable")
    public FullTableModelPage getTable(
            @RequestParam Long id,
            @ModelAttribute SearchModel searchModel,
            @PageableDefault Pageable pageable
    ) {
        log.info(searchModel.toString());
        return patternTableService.getTable(id, searchModel, pageable);
    }

    @GetMapping("exist/{name}")
    public boolean existByName(@PathVariable String name) {
        return patternTableRepo.existsByNameTable(name);
    }

    @GetMapping("/{id}")
    @Cacheable(cacheNames = "getByPatternTableId")
    public PatternTable getById(@PathVariable Long id) {
        return patternTableRepo.findById((long) id);
    }

    @GetMapping("/getAllOldVersions")
    public Page<PatternTable> getAllOldVersions(@RequestParam String oldName, @PageableDefault(sort = "dateKill", direction = Sort.Direction.DESC) Pageable pageable) {
        return patternTableRepo.findAllByOldNameAndIsActive(oldName, false, pageable);
    }

    @GetMapping("/getAll")
    @Cacheable(cacheNames = "getAllPatternTable")
    public Page<PatternTable> getAll(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByIsActive(pageable, true);
    }

    @PostMapping("/getAllSort")
    public Page<PatternTable> getAllSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAll/{patternId}")
    @Cacheable(cacheNames = "getAllPatternTableByPatternId")
    public Page<PatternTable> getAllByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsActive(patternId, pageable, true);
    }

    @GetMapping("/getAllBySource/{sourceId}")
    @Cacheable(cacheNames = "getAllPatternTableBySourceId")
    public Page<PatternTable> getAllBySourceId(@PageableDefault(sort = "id") Pageable pageable, @PathVariable Long sourceId) {
        return patternTableRepo.findAllBySourceIdAndIsActive(sourceId, pageable, true);
    }

    @PostMapping("/getAllSort/{patternId}")
    public Page<PatternTable> getAllByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @PostMapping("/getAllBySourceSort/{sourceId}")
    public Page<PatternTable> getAllBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive")
    @Cacheable(cacheNames = "getAllPatternTableArchive")
    public Page<PatternTable> getAllArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByIsArchiveAndIsActive(true, pageable, true);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<PatternTable> getAllArchiveSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.setIsActive(true);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive/{patternId}")
    @Cacheable(cacheNames = "getAllPatternTableArchiveByPatternId")
    public Page<PatternTable> getAllArchiveByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchiveAndIsActive(patternId, true, pageable, true);
    }

    @PostMapping("/getAllArchiveSort/{patternId}")
    public Page<PatternTable> getAllArchiveByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchiveAndIsActive(sourceId, true, pageable, true);
    }

    @PostMapping("/getAllArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive")
    @Cacheable(cacheNames = "getAllPatternTableNotArchive")
    public Page<PatternTable> getAllNotArchive(@PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByIsArchiveAndIsActive(false, pageable, true);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<PatternTable> getAllNotArchiveSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive/{patternId}")
    @Cacheable(cacheNames = "getAllPatternTableNotArchiveByPatternId")
    public Page<PatternTable> getAllNotArchiveByPatternId(@PathVariable Long patternId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchiveAndIsActive(patternId, false, pageable, true);
    }

    @PostMapping("/getAllNotArchiveSort/{patternId}")
    public Page<PatternTable> getAllNotArchiveByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchiveBySourceId/{sourceId}")
    @Cacheable(cacheNames = "getAllPatternTableNotArchiveBySourceId")
    public Page<PatternTable> getAllNotArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchiveAndIsActive(sourceId, false, pageable, true);
    }

    @PostMapping("/getAllNotArchiveSortBySourceIdSort/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        pattern.setIsActive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/archive/{id}")
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
    public PatternTable archivePattern(@PathVariable Long id) {
        return patternTableService.archivePatternTable(id);
    }

    @GetMapping("/deArchive/{id}")
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
    public PatternTable deArchivePattern(@PathVariable Long id) {
        return patternTableService.deArchivePatternTable(id);
    }

    @GetMapping("/archivePatterns/{id}")
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
    public List<PatternTable> archivePatterns(@PathVariable Long id) {
        return patternTableService.archivePatternTablesByPatternId(id);
    }

    @GetMapping("/deArchivePatterns/{id}")
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
    public List<PatternTable> deArchivePatterns(@PathVariable Long id) {
        return patternTableService.deArchivePatternTablesByPatternId(id);
    }

    @GetMapping("/archivePatternsBySource/{id}")
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
    public List<PatternTable> archivePatternsBySource(@PathVariable Long id) {
        return patternTableService.archivePatternTablesBySourceId(id);
    }

    @GetMapping("/deArchivePatternTablesBySource/{id}")
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
    public List<PatternTable> deArchivePatternsBySource(@PathVariable Long id) {
        return patternTableService.deArchivePatternTablesBySourceId(id);
    }

}
