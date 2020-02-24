package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;

import java.util.List;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class PatternController {

    final PatternRepo patternRepo;

    public PatternController(PatternRepo patternRepo) {
        this.patternRepo = patternRepo;
    }

    @PostMapping("/create")
    @ResponseBody
    public String createPattern(
            @RequestParam(value = "file", required = false) MultipartFile multipartFile,
            @RequestParam(value = "json", required = false) String json,
            @RequestParam Pattern id
            ){

        log.info("хуй");
        log.info(id.toString());

        return "ok";
    }

    @GetMapping("{id}")
    public List<Pattern> getPattern(@PathVariable Long id){
        return patternRepo.findBySourceId(id);
    }
}