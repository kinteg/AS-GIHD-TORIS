package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelCreator;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.export.ExportDataFromDbService;
import ru.iac.ASGIHDTORIS.spring.service.table.TableCreatorService;

import java.util.List;

@Controller
@RequestMapping("api/tableCreator/")
@RestController
public class PatternTableController {

    private final TableCreatorService tableCreatorService;
    private final DataModelCreator dataModelCreator;
    private final PatternTableRepo patternTableRepo;
    private final ExportDataFromDbService exportDataFromDbService;

    public PatternTableController(TableCreatorService tableCreatorService, DataModelCreator dataModelCreator, PatternTableRepo patternTableRepo, ExportDataFromDbService exportDataFromDbService) {
        this.tableCreatorService = tableCreatorService;
        this.dataModelCreator = dataModelCreator;
        this.patternTableRepo = patternTableRepo;
        this.exportDataFromDbService = exportDataFromDbService;
    }

    @PostMapping("/create")
    @ResponseBody
    public boolean createPattern(
            @RequestBody TableModel tableModel,
            @RequestParam List<String> names,
            @RequestParam List<String> types,
            @RequestParam List<Boolean> primaries,
            @RequestParam Long id
    ) {

        if (id == null) {
            return false;
        }

        dataModelCreator.setDataModel(names, types, primaries);
        List<DataModel> dataModels = dataModelCreator.getDataModel();
        tableModel.setModels(dataModels);

        return tableCreatorService.addTable(tableModel, id);
    }

    @GetMapping("getTable")
    public FullTableModelPage getTable(
            @RequestParam Long id,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false, defaultValue = "") String nameColumn) {

        if (patternTableRepo.existsById(id)) {

            return exportDataFromDbService.getFullTableModel(
                    patternTableRepo.findById((long) id),
                    pageable,
                    nameColumn
            );
        }

        return new FullTableModelPage();

    }

    @GetMapping("/getAll")
    public Page<PatternTable> getAll(@RequestParam Long patternId, @PageableDefault Pageable pageable){
        return patternTableRepo.findAllByPatternId(patternId, pageable);
    }

    @GetMapping("/{id}")
    public PatternTable getById(@PathVariable Long id){
        return patternTableRepo.findById((long)id);
    }

}
