package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternDateModel;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceDateModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

        pattern.setDateCreation(LocalDateTime.now());
        pattern.setDateActivation(LocalDateTime.now());
        pattern.setLastUpdate(LocalDateTime.now());

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

    @PostMapping("/getAllSort")
    public Page<Pattern> getAll(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable) {
        return null;
    }

    @GetMapping("/getAll/{sourceId}")
    public Page<Pattern> getAll(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{sourceId}")
    public Page<Pattern> getAllWithSourceId(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable){
        return null;
    }

    @GetMapping("/getAllArchive")
    public Page<Pattern> getAllArchive(@PageableDefault Pageable pageable){
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Pattern> getAllArchive(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable){
        pattern.setIsArchive(true);
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllArchive/{sourceId}")
    public Page<Pattern> getAllArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @PostMapping("/getAllArchiveSort/{sourceId}")
    public Page<Pattern> getAllArchiveWithSourceId(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable){
        pattern.setIsArchive(true);
        return null;
    }

    @GetMapping("/getAllNotArchive")
    public Page<Pattern> getAllNotArchive(@PageableDefault Pageable pageable){
        return patternRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Pattern> getAllNotArchive(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable){
        pattern.setIsArchive(true);
        return null;
    }

    @GetMapping("/getAllNotArchive/{sourceId}")
    public Page<Pattern> getAllNotArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable){
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort/{sourceId}")
    public Page<Pattern> getAllNotArchiveWithSourceId(@ModelAttribute PatternDateModel pattern, @PageableDefault Pageable pageable){
        pattern.setIsArchive(false);
        return null;
    }

    @GetMapping("/archive/{id}")
    public boolean archivePattern(@PathVariable Long id){

        if (id != null && patternRepo.existsById(id)) {
            Pattern pattern = patternRepo.findById((long)id);
            pattern.setIsArchive(true);
            pattern.setDateActivation(LocalDateTime.now());

            patternRepo.save(pattern);

            return true;
        }

        return false;
    }

    @GetMapping("/deArchive/{id}")
    public boolean deArchivePattern(@PathVariable Long id){

        if (id != null && patternRepo.existsById(id)) {
            Pattern pattern = patternRepo.findById((long)id);
            pattern.setIsArchive(false);
            pattern.setDateActivation(LocalDateTime.now());

            patternRepo.save(pattern);

            return true;
        }

        return false;
    }


    @GetMapping("/archivePatterns/{sourceId}")
    public boolean archivePatterns(@PathVariable Long sourceId) {

        if (sourceId != null &&
                patternRepo.existsBySourceId(sourceId)) {

            List<Pattern> patterns = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternRepo.saveAll(patterns);

            return true;
        }

        return false;
    }

    @GetMapping("/deArchivePatterns/{sourceId}")
    public boolean deArchivePatterns(@PathVariable Long sourceId) {

        if (sourceId != null &&
                patternRepo.existsBySourceId(sourceId)) {

            List<Pattern> patterns = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternRepo.saveAll(patterns);

            return true;
        }

        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Pattern Pattern) {

        if (patternRepo.existsById(Pattern.getId())
                && patternValidator.isValid(Pattern)) {

            patternRepo.save(Pattern);

            return true;
        }

        return false;
    }

}
