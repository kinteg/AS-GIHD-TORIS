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
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
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
    private final LoggerSender<PatternTable> patternTableLoggerSender;
    private final BeforeAfter<PatternTable> patternTableBeforeAfter;

    public PatternTableController(
            TableCreatorService tableCreatorService,
            DataModelCreator dataModelCreator,
            PatternTableRepo patternTableRepo,
            PatternTableRepo2 patternTableRepo2,
            TableRepo tableRepo,
            @Qualifier("dataModelListValidator") Validator<DataModelList> dataModelListValidator,
            @Qualifier("patternIdValidator") Validator<Long> patternIdValidator,
            @Qualifier("patternTableLoggerSender") LoggerSender<PatternTable> patternTableLoggerSender,
            @Qualifier("patternTableBeforeAfter") BeforeAfter<PatternTable> patternTableBeforeAfter) {

        this.tableCreatorService = tableCreatorService;
        this.dataModelCreator = dataModelCreator;
        this.patternTableRepo = patternTableRepo;
        this.patternTableRepo2 = patternTableRepo2;
        this.tableRepo = tableRepo;
        this.dataModelListValidator = dataModelListValidator;
        this.patternIdValidator = patternIdValidator;
        this.patternTableLoggerSender = patternTableLoggerSender;
        this.patternTableBeforeAfter = patternTableBeforeAfter;
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

        PatternTableModelStatus patternAfter;
        long loggerId;

        if (!patternIdValidator.isValid(patternId)) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else if (patternTableRepo.existsByNameTable(tableModel.getTableName())) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-2")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else if (!dataModelListValidator.isValid(dataModelList)) {
            patternAfter = PatternTableModelStatus
                    .builder()
                    .tableModel(TableModelStatus.emptyTableModelStatus())
                    .patternTable(PatternTable.builder().id(Long.parseLong("-1")).build())
                    .build();
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());
        } else {

            dataModelCreator.setDataModel(dataModelList);
            List<DataModel> dataModels = dataModelCreator.getDataModel();
            tableModel.setModels(dataModels);

            patternAfter = tableCreatorService.addTable(tableModel, patternId);
            log.info("qwe" + patternAfter.toString());
            loggerId = patternTableLoggerSender.afterCreate(patternAfter.getPatternTable());

        }

        if (patternAfter.getPatternTable().getId() > 0) {
            patternTableBeforeAfter.afterCreate(patternAfter.getPatternTable(), loggerId);
        }

        log.info("qwe" + patternAfter.toString());

        return patternAfter;
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
        PatternTable patternBefore, patternAfter;

        if (id == null) {
            patternAfter = PatternTable.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternTableRepo.existsById(id)) {
            patternAfter = PatternTable.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternTableRepo.findById((long) id);
            patternBefore = PatternTable
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();
            patternAfter.setIsArchive(true);
            patternAfter.setDateDeactivation(LocalDateTime.now());

            patternTableRepo.save(patternAfter);

        }

        long loggerId = patternTableLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternTableBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
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
        PatternTable patternBefore, patternAfter;

        if (id == null) {
            patternAfter = PatternTable.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternTableRepo.existsById(id)) {
            patternAfter = PatternTable.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternTableRepo.findById((long) id);
            patternBefore = PatternTable
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();
            patternAfter.setIsArchive(false);
            patternAfter.setDateActivation(LocalDateTime.now());

            patternTableRepo.save(patternAfter);

        }

        long loggerId = patternTableLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternTableBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
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
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternTableRepo.existsByPatternId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateDeactivation(v.getDateDeactivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

        }

        List<Long> loggerId = patternTableLoggerSender.afterArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternTableRepo.saveAll(patternsAfter);
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
    public List<PatternTable> deArchivePatterns(@PathVariable Long id) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternTableRepo.existsByPatternId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateActivation(v.getDateActivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllByPatternId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

        }

        List<Long> loggerId = patternTableLoggerSender.afterDeArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterDeArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternTableRepo.saveAll(patternsAfter);
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
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternTableRepo.existsBySourceId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateDeactivation(v.getDateDeactivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

        }

        List<Long> loggerId = patternTableLoggerSender.afterArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternTableRepo.saveAll(patternsAfter);
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
    @GetMapping("/deArchivePatternsBySource/{id}")
    public List<PatternTable> deArchivePatternsBySource(@PathVariable Long id) {
        List<PatternTable> patternsBefore, patternsAfter;

        if (id == null) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternTableRepo.existsBySourceId(id)) {
            patternsAfter = Collections.singletonList(PatternTable.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .map(v -> PatternTable
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateActivation(v.getDateActivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternTableRepo
                    .findAllBySourceId(id)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

        }

        List<Long> loggerId = patternTableLoggerSender.afterDeArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternTableBeforeAfter.afterDeArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternTableRepo.saveAll(patternsAfter);
    }

    @GetMapping("exist/{name}")
    public boolean existByName(@PathVariable String name) {
        return patternTableRepo.existsByNameTable(name);
    }

}
