package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo2;
import ru.iac.ASGIHDTORIS.spring.service.export.ExportDataFromDbService;
import ru.iac.ASGIHDTORIS.spring.service.table.TableCreatorService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/tableCreator/")
@RestController
public class PatternTableController {

    private final TableCreatorService tableCreatorService;
    private final DataModelCreator dataModelCreator;
    private final PatternTableRepo patternTableRepo;
    private final PatternTableRepo2 patternTableRepo2;
    private final ExportDataFromDbService exportDataFromDbService;

    public PatternTableController(TableCreatorService tableCreatorService, DataModelCreator dataModelCreator, PatternTableRepo patternTableRepo, PatternTableRepo2 patternTableRepo2, ExportDataFromDbService exportDataFromDbService) {
        this.tableCreatorService = tableCreatorService;
        this.dataModelCreator = dataModelCreator;
        this.patternTableRepo = patternTableRepo;
        this.patternTableRepo2 = patternTableRepo2;
        this.exportDataFromDbService = exportDataFromDbService;
    }

    @PostMapping("/create")
    @ResponseBody
    public boolean createPattern(
            @RequestBody TableModel tableModel,
            @RequestParam List<String> names,
            @RequestParam List<String> types,
            @RequestParam List<Boolean> primaries,
            @RequestParam Long patternId
    ) {

        if (patternId == null) {
            return false;
        }

        dataModelCreator.setDataModel(names, types, primaries);
        List<DataModel> dataModels = dataModelCreator.getDataModel();
        tableModel.setModels(dataModels);

        return tableCreatorService.addTable(tableModel, patternId);
    }

    @GetMapping("getTable")
    public FullTableModelPage getTable(
            @RequestParam Long id,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String nameColumn,
            @RequestParam(required = false, defaultValue = "") String sort
    ) {

        if (patternTableRepo.existsById(id)) {

            return exportDataFromDbService.getFullTableModel(
                    patternTableRepo.findById((long) id),
                    pageable,
                    nameColumn,
                    sort
            );
        }

        return new FullTableModelPage();

    }

    @GetMapping("/{id}")
    public PatternTable getById(@PathVariable Long id) {
        return patternTableRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<PatternTable> getAll(@PageableDefault Pageable pageable){
        return patternTableRepo.findAll(pageable);
    }

    @GetMapping("/getAllSort")
    public Page<PatternTable> getAll(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAll/{patternId}")
    public Page<PatternTable> getAll(@PathVariable Long patternId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllByPatternId(patternId, pageable);
    }

    @GetMapping("/getAllBySource/{sourceId}")
    public Page<PatternTable> getAllBySource(@PageableDefault Pageable pageable, @PathVariable Long sourceId){
        return patternTableRepo.findAllBySourceId(sourceId, pageable);
    }

    @GetMapping("/getAllSort/{patternId}")
    public Page<PatternTable> getAllWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllBySourceSort/{sourceId}")
    public Page<PatternTable> getAllWithSourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive")
    public Page<PatternTable> getAllArchive(@PageableDefault Pageable pageable){
        return patternTableRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllArchiveSort")
    public Page<PatternTable> getAllArchive(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive/{patternId}")
    public Page<PatternTable> getAllArchive(@PathVariable Long patternId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, true, pageable);
    }

    @GetMapping("/getAllArchiveSort/{patternId}")
    public Page<PatternTable> getAllArchiveWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @GetMapping("/getAllArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllArchiveWithSourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive")
    public Page<PatternTable> getAllNotArchive(@PageableDefault Pageable pageable){
        return patternTableRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort")
    public Page<PatternTable> getAllNotArchive(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive/{patternId}")
    public Page<PatternTable> getAllNotArchive(@PathVariable Long patternId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllByPatternIdAndIsArchive(patternId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSort/{patternId}")
    public Page<PatternTable> getAllNotArchiveWithPatternId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternTableRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchiveBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveBySourceId(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @GetMapping("/getAllNotArchiveSortBySourceId/{sourceId}")
    public Page<PatternTable> getAllNotArchiveWithPatternIdBySourceId(@ModelAttribute PatternTableModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel){
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
