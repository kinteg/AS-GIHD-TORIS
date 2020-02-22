package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.Source;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.SourceRepo;

import java.util.List;

@RestController
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final PatternRepo patternRepo;

    public SourceController(SourceRepo sourceRepo, PatternRepo patternRepo) {
        this.sourceRepo = sourceRepo;
        this.patternRepo = patternRepo;
    }

//TODO подгружать сразу все шаблоны в поле ввода в поиске
 
    @PostMapping("/create")
    public Source createSource (@RequestBody Source name){
        log.info(name.toString());
        return sourceRepo.save(name);
    }

//TODO ДОДЕЛАТЬ

    @GetMapping("/getAll")
    public List<Source> getAllSource(){
        log.info(sourceRepo.findAll().toString());
        return sourceRepo.findAll();
    }

    @GetMapping("{name}")
    public Source findByName(@PathVariable String name) {
        return sourceRepo.findByName(name);
    }
}
