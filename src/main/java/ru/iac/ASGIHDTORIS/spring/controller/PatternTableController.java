package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;
import ru.iac.ASGIHDTORIS.spring.repo.TableRepo;
import ru.iac.ASGIHDTORIS.spring.service.table.TableCreatorService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("api/tableCreator/")
@RestController
public class PatternTableController {

    private final TableRepo tableRepo;
    private final DataModelCreator dataModelCreator;
    private final PatternTableRepo patternTableRepo;
    private final PatternTableRepo2 patternTableRepo2;
    private final Validator<DataModelList> dataModelListValidator;
    private final TableCreatorService tableCreatorService;
    private final Validator<Long> patternIdValidator;

    public PatternTableController(
            TableCreatorService tableCreatorService,
            DataModelCreator dataModelCreator,
            PatternTableRepo patternTableRepo,
            PatternTableRepo2 patternTableRepo2,
            TableRepo tableRepo,
            @Qualifier("dataModelListValidator") Validator<DataModelList> dataModelListValidator,
            @Qualifier("patternIdValidator") Validator<Long> patternIdValidator) {

        this.tableCreatorService = tableCreatorService;
        this.dataModelCreator = dataModelCreator;
        this.patternTableRepo = patternTableRepo;
        this.patternTableRepo2 = patternTableRepo2;
        this.tableRepo = tableRepo;
        this.dataModelListValidator = dataModelListValidator;
        this.patternIdValidator = patternIdValidator;
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @PostMapping("/create")
    @ResponseBody
    public PatternTableModelStatus createPattern(
            @ModelAttribute TableModel tableModel,
            @ModelAttribute DataModelList dataModelList,
            @RequestParam Long patternId
    ) {

        if (!patternIdValidator.isValid(patternId)) {
            return PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
        } else if (patternTableRepo.existsByNameTable(tableModel.getTableName())) {
            return PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-2")).build())
                    .build();
        } else if (!dataModelListValidator.isValid(dataModelList)) {
            return PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
        } else {

            dataModelCreator.setDataModel(dataModelList);
            List<DataModel> dataModels = dataModelCreator.getDataModel();
            tableModel.setModels(dataModels);

            return tableCreatorService.addTable(tableModel, patternId);
        }
    }

    @Cacheable(cacheNames = "getTable")
    @PostMapping("/getTable")
    public FullTableModelPage getTable(
            @RequestParam Long id,
            @ModelAttribute SearchModel searchModel,
            @PageableDefault Pageable pageable
    ) {

        if (patternTableRepo.existsById(id)) {

            PatternTable patternTable = patternTableRepo.findById((long) id);
            searchModel.setPageable(pageable);
            return tableRepo.getTable(patternTable.getNameTable(), patternTable.getNameFile(), searchModel);
        }

        return new FullTableModelPage();

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

    @Cacheable(cacheNames = "getAllPatternTableSort")
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

    @Cacheable(cacheNames = "getAllPatternTableByPatternIdSort")
    @PostMapping("/getAllSort/{patternId}")
    public Page<PatternTable> getAllByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        log.info(pattern.toString());
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableBySourceIdSort")
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

    @Cacheable(cacheNames = "getAllPatternTableArchiveSort")
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

    @Cacheable(cacheNames = "getAllPatternTableArchiveByPatternIdSort")
    @GetMapping("/getAllArchiveSort/{patternId}")
    public Page<PatternTable> getAllArchiveByPatternIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @Cacheable(cacheNames = "getAllPatternTableArchiveBySourceId")
    @GetMapping("/getAllArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault(sort = "id") Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @Cacheable(cacheNames = "getAllPatternTableArchiveBySourceIdSort")
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

    @Cacheable(cacheNames = "getAllPatternTableNotArchiveSort")
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

    @Cacheable(cacheNames = "getAllPatternTableNotArchiveByPatternIdSort")
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

    @Cacheable(cacheNames = "getAllPatternTableNotArchiveBySourceIdSort")
    @GetMapping("/getAllNotArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceIdSort(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/archive/{id}")
    public PatternTable archivePattern(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsById(id)) {
            PatternTable pattern = patternTableRepo.findById((long) id);
            pattern.setIsArchive(true);
            pattern.setDateActivation(LocalDateTime.now());

            patternTableRepo.save(pattern);

            return pattern;
        }

        return new PatternTable();
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/deArchive/{id}")
    public PatternTable deArchivePattern(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsById(id)) {
            PatternTable pattern = patternTableRepo.findById((long) id);
            pattern.setIsArchive(false);
            pattern.setDateActivation(LocalDateTime.now());

            patternTableRepo.save(pattern);

            return pattern;
        }

        return new PatternTable();
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/archivePatterns/{id}")
    public List<PatternTable> archivePatterns(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsByPatternId(id)) {
            List<PatternTable> patterns = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternTableRepo.saveAll(patterns);

            return patterns;
        }

        return Collections.emptyList();
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/deArchivePatterns/{id}")
    public List<PatternTable> deArchivePatterns(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsByPatternId(id)) {
            List<PatternTable> patterns = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternTableRepo.saveAll(patterns);

            return patterns;
        }

        return Collections.emptyList();
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/archivePatternsBySource/{id}")
    public List<PatternTable> archivePatternsBySource(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsBySourceId(id)) {
            List<PatternTable> patterns = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternTableRepo.saveAll(patterns);

            return patterns;
        }

        return Collections.emptyList();
    }

    @CacheEvict(value =
            "getTable, getByPatternTableId, " +
                    "getAllPatternTable, getAllPatternTableSort, " +
                    "getAllPatternTableByPatternId, getAllPatternTableBySourceId, " +
                    "getAllPatternTableByPatternIdSort, getAllPatternTableBySourceIdSort" +
                    "getAllPatternTableArchive, getAllPatternTableArchiveSort" +
                    "getAllPatternTableArchiveByPatternId, getAllPatternTableArchiveByPatternIdSort" +
                    "getAllPatternTableArchiveBySourceId, getAllPatternTableArchiveBySourceIdSort" +
                    "getAllPatternTableNotArchive, getAllPatternTableNotArchiveSort" +
                    "getAllPatternTableNotArchiveByPatternId, getAllPatternTableNotArchiveByPatternIdSort" +
                    "getAllPatternTableNotArchiveBySourceId, getAllPatternTableNotArchiveBySourceIdSort" +
                    "existByPatternTableName",
            allEntries = true)
    @GetMapping("/deArchivePatternsBySource/{id}")
    public List<PatternTable> deArchivePatternsBySource(@PathVariable Long id) {

        if (id != null && patternTableRepo.existsBySourceId(id)) {
            List<PatternTable> patterns = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternTableRepo.saveAll(patterns);

            return patterns;
        }

        return Collections.emptyList();
    }

    @Cacheable(cacheNames = "existByPatternTableName")
    @GetMapping("exist/{name}")
    public boolean existByName(@PathVariable String name) {
        return patternTableRepo.existsByNameTable(name);
    }

}
