package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class PatternController {
    
    private final Validator<Pattern> patternValidator;
    private final PatternRepo patternRepo;

    public PatternController(@Qualifier("getPatternValidator") Validator<Pattern> patternValidator, PatternRepo patternRepo) {
        this.patternValidator = patternValidator;
        this.patternRepo = patternRepo;
    }

    @PostMapping("/create")
    public Pattern createPattern(@RequestBody Pattern pattern) {
        return patternValidator.isValid(pattern)
                ? patternRepo.save(pattern)
                : new Pattern();
    }

    @GetMapping("/{id}")
    public Pattern getById(@PathVariable Long id) {
        return patternRepo.findById((long)id);
    }

    @GetMapping("/getAll")
    public Page<Pattern> getAll(@PageableDefault Pageable pageable) {
        return patternRepo.findAll(pageable);
    }

    @GetMapping("/getAll/{sourceId}")
    public Page<Pattern> getAll(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceId(sourceId, pageable);
    }

    @GetMapping("/getAllArchive")
    public Page<Pattern> getAllArchive(@PageableDefault Pageable pageable){
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllArchive/{sourceId}")
    public Page<Pattern> getAllArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Pattern> getAllNotArchive(@PageableDefault Pageable pageable){
        return patternRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/getAllNotArchive/{sourceId}")
    public Page<Pattern> getAllNotArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @GetMapping("/archive/{id}")
    public boolean archivePattern(@PathVariable Long id){

        if (patternRepo.existsById(id)) {
            Pattern pattern = patternRepo.findById((long)id);
            pattern.setIsArchive(true);

            patternRepo.save(pattern);

            return true;
        }

        return false;
    }

    @GetMapping("/deArchive/{id}")
    public boolean deArchivePattern(@PathVariable long id){

        if (patternRepo.existsById(id)) {
            Pattern pattern = patternRepo.findById(id);
            pattern.setIsArchive(false);

            patternRepo.save(pattern);

            return true;
        }

        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Pattern Pattern) {

        if (patternRepo.existsById(Pattern.getId()) && patternValidator.isValid(Pattern)) {
            patternRepo.save(Pattern);

            return true;
        }

        return false;
    }

}
