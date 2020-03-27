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
    @ResponseBody
    public Source createSource (@ModelAttribute Source source){

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

    @PostMapping("/getAllSort")
    public Page<Source> getAll(@PageableDefault Pageable pageable, @RequestParam String key, @RequestParam String sort, @ModelAttribute Source source, @RequestParam LocalDateTime dateCreation2, @RequestParam LocalDateTime dateDeactivation2, @RequestParam LocalDateTime dateActivation2, @RequestParam LocalDateTime lastUpdate2, @RequestParam LocalDateTime dateCreation1, @RequestParam LocalDateTime dateDeactivation1, @RequestParam LocalDateTime dateActivation1, @RequestParam LocalDateTime lastUpdate1) {
        log.info(source.toString());
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).descending());
        } else {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(key).ascending());
        }
        log.info(sourceRepo.findAll(pageable).toString());
        return sourceRepo.findAllNative(
                pageable,
                source.getName(),
                source.getLongName(),
                source.getShortName(),
                source.getDescription(),
                source.getAddDescription(),
                source.getScope(),
                source.getPeriodicity(),
                source.getRenewalPeriod(),
                source.getType(),
                source.getTags(),
                source.getProviderLink(),
                source.getDataSource(),
                source.getDateCreation(),
                dateCreation1,
                dateCreation2,
                source.getDateDeactivation(),
                dateDeactivation1,
                dateDeactivation2,
                source.getDateActivation(),
                dateActivation1,
                dateActivation2,
                source.getLastUpdate(),
                lastUpdate1,
                lastUpdate2,
                source.getIsArchive()
        );

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
    @ResponseBody
    public Source update(@ModelAttribute Source source) {

        if (sourceRepo.existsById(source.getId()) && validator.isValid(source)) {
            sourceRepo.save(source);
            source.setLastUpdate(LocalDateTime.now());

            return source;
        }

        return new Source();
    }

}
