package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.domain.Source;
import ru.iac.ASGIHDTORIS.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.service.exporer.DbParserService;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final PatternTableRepo patternTableRepo;
    private final DbParserService parserService;

    private final String DEFAULT_LIMIT_VALUE = "2";

    public SourceController(SourceRepo sourceRepo, PatternTableRepo patternTableRepo, DbParserService parserService) {
        this.sourceRepo = sourceRepo;
        this.patternTableRepo = patternTableRepo;
        this.parserService = parserService;
    }

//TODO подгружать сразу все шаблоны в поле ввода в поиске
 
    @PostMapping("/create")
    public Source createSource (@RequestBody Source name){
        return sourceRepo.save(name);
    }

//TODO ДОДЕЛАТЬ

    @GetMapping("/getAll")
    public List<Source> getAllSource(){
        return sourceRepo.findAll();
    }

    //TODO tyt
    @GetMapping("/getTable/{id}")
    public String findTableById(
            @PathVariable Long id,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT_VALUE)
                    Long limit) throws SQLException {

        log.info(parserService.getData(id, limit));
        return parserService.getData(id, limit);
    }

    @GetMapping("{name}")
    public Source findByName(@PathVariable String name) {
        return sourceRepo.findByName(name);
    }
}
