package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

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

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @PostMapping("/update")
    public Source update(@RequestBody Source source) {

        if (sourceRepo.existsById(source.getId()) && validator.isValid(source)) {
            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

}
