package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.service.pattern.PatternCreatorService;

import java.util.List;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class PatternController {

    private final PatternRepo patternRepo;
    private final PatternCreatorService patternCreatorService;

    public PatternController(PatternRepo patternRepo, PatternCreatorService patternCreatorService) {
        this.patternRepo = patternRepo;
        this.patternCreatorService = patternCreatorService;
    }

    @PostMapping("/create")
    @ResponseBody
    public String createPattern(
            @RequestParam List<String> json,
            @ModelAttribute Pattern pattern
            ) {

        log.info(pattern.toString());
        log.info(json.toString());
//        log.info(json.split("},\\{")[0]);
//        patternCreatorService.create(json.get(o), pattern)
        return patternCreatorService.create(json.toString(), pattern);
    }

    @GetMapping("{id}")
    public List<Pattern> getPattern(@PathVariable Long id){
        return patternRepo.findBySourceId(id);
    }
}