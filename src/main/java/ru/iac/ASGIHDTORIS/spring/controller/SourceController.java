package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final Validator<Source> validator;

    public SourceController(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator
    ) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
    }

    @PostMapping("/create")
    public Source createSource (@RequestBody Source source){

        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        return validator.isValid(source)
                ? sourceRepo.save(source)
                : new Source();
    }

    @GetMapping("/{id}")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long)id);
    }

    @GetMapping("/getAll")
    public Page<Source> getAll(@PageableDefault Pageable pageable){
        return sourceRepo.findAll(pageable);
    }

    @GetMapping("/getAllSource")
    public Page<Source> getAll(@PageableDefault Pageable pageable, @ModelAttribute Source source, @PathVariable String key, @PathVariable String sort) {

        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).descending());
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).ascending());
        }

        return sourceRepo.findAllSourceWithPagination(pageable, source.getName());

    }

    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault Pageable pageable){
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault Pageable pageable){
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(true);
            source.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(false);
            source.setDateActivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @PostMapping("/update")
    public Source update(@ModelAttribute Source source) {

        if (sourceRepo.existsById(source.getId()) && validator.isValid(source)) {
            sourceRepo.save(source);
            source.setLastUpdate(LocalDateTime.now());

            return source;
        }

        return new Source();
    }

}
