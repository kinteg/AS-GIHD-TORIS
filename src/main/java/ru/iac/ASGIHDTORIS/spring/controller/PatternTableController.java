package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
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
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
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

    @PostMapping("/create")
    @ResponseBody
    public boolean createPattern(
            @ModelAttribute TableModel tableModel,
            @ModelAttribute DataModelList dataModelList,
            @RequestParam Long patternId
    ) {
        if (!patternIdValidator.isValid(patternId)
                || patternTableRepo.existsByNameTable(tableModel.getTableName())
                || !dataModelListValidator.isValid(dataModelList)) {

            return false;
        }
        dataModelCreator.setDataModel(dataModelList);
        List<DataModel> dataModels = dataModelCreator.getDataModel();
        tableModel.setModels(dataModels);
        return tableCreatorService.addTable(tableModel, patternId);
    }

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

    @GetMapping("/{id}")
    public PatternTable getById(@PathVariable Long id) {
        return patternTableRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<PatternTable> getAll(@PageableDefault Pageable pageable) {
        return patternTableRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<PatternTable> getAll(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAll/{patternId}")
    public Page<PatternTable> getAll(@PathVariable Long patternId, @PageableDefault Pageable pageable) {
        return patternTableRepo.findAllByPatternId(patternId, pageable);
    }

    @GetMapping("/getAllBySource/{sourceId}")
    public Page<PatternTable> getAllBySource(@PageableDefault Pageable pageable, @PathVariable Long sourceId) {
        return patternTableRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{patternId}")
    public Page<PatternTable> getAllWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        log.info(pattern.toString());
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllBySourceSort/{sourceId}")
    public Page<PatternTable> getAllWithSourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive")
    public Page<PatternTable> getAllArchive(@PageableDefault Pageable pageable) {
        return patternTableRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllArchiveSort")
    public Page<PatternTable> getAllArchive(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive/{patternId}")
    public Page<PatternTable> getAllArchive(@PathVariable Long patternId, @PageableDefault Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, true, pageable);
    }

    @GetMapping("/getAllArchiveSort/{patternId}")
    public Page<PatternTable> getAllArchiveWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @GetMapping("/getAllArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveWithSourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive")
    public Page<PatternTable> getAllNotArchive(@PageableDefault Pageable pageable) {
        return patternTableRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort")
    public Page<PatternTable> getAllNotArchive(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive/{patternId}")
    public Page<PatternTable> getAllNotArchive(@PathVariable Long patternId, @PageableDefault Pageable pageable) {
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort/{patternId}")
    public Page<PatternTable> getAllNotArchiveWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault Pageable pageable) {
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveWithPatternIdBySourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }


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

    @GetMapping("exist/{name}")
    public boolean existByName(@PathVariable String name) {
        return patternTableRepo.existsByNameTable(name);
    }

}
