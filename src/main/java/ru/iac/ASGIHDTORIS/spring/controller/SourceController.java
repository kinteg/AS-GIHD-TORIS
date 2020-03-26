package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

@RestController
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
        log.info(validator.isValid(source) + "");
        log.info(source.toString());
        return validator.isValid(source)
                ? sourceRepo.save(source)
                : new Source();
    }

    @GetMapping("/{id}")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long)id);
    }

    //возвращает лист + текущая стр и кол-во эл-тов(10)
    @GetMapping("/getAll")
    public Page<Source> getAll(@PageableDefault Pageable pageable){
        return sourceRepo.findAll(pageable);
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
    public boolean archiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(true);

            sourceRepo.save(source);

            return true;
        }

        return false;
    }

    @GetMapping("/deArchive/{id}")
    public boolean deArchiveSource(@PathVariable long id){

        if (sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById(id);
            source.setIsArchive(false);

            sourceRepo.save(source);

            return true;
        }

        return false;
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Source source) {

        if (sourceRepo.existsById(source.getId()) && validator.isValid(source)) {
            sourceRepo.save(source);

            return true;
        }

        return false;
    }

}
