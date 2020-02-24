package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.service.pattern.PatternCreatorService;

import java.io.IOException;
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
            @RequestParam(value = "json") List<String> json,
            @ModelAttribute Pattern pattern
            ) {

        log.info(pattern.toString());
        log.info(json.toString());

        return patternCreatorService.create(json, pattern);
    }

    @GetMapping("{id}")
    public List<Pattern> getPattern(@PathVariable Long id){
        return patternRepo.findBySourceId(id);
    }
}