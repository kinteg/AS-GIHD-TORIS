package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.service.source.SourceService;

import java.time.LocalDateTime;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final Validator<Source> validator;
    private final SourceService sourceService;
    private final SourceService sourceService2;

    public SourceController(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator,
            @Qualifier("sourceServiceImpl") SourceService sourceService,
            @Qualifier("sourceServiceImpl2") SourceService sourceService2
    ) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
        this.sourceService = sourceService;
        this.sourceService2 = sourceService2;
    }

    @PostMapping("/create")
    @ResponseBody
    public Source createSource(@ModelAttribute Source source) {

        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        return validator.isValid(source)
                && !sourceRepo.existsByShortName(source.getShortName())
                ? sourceRepo.save(source)
                : new Source();
    }

    @GetMapping("/{id}")
    public Source getById(@PathVariable Long id) {
        return sourceRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<Source> getAll(@PageableDefault Pageable pageable) {
        return sourceRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Source> getAll(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable) {
        return sourceService2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Source> getAllArchive(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable) {
        source.setIsArchive(true);
        return sourceService.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Source> getAllNotArchive(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable) {
        source.setIsArchive(false);
        return sourceService.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable Long id) {

        if (id != null && sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById((long) id);
            source.setIsArchive(true);
            source.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }

    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable Long id) {

        if (id != null && sourceRepo.existsById(id)) {
            Source source = sourceRepo.findById((long)id);
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

        if (source.getId() != null
                && sourceRepo.existsById(source.getId())
                && validator.isValid(source)) {

            if (sourceRepo.existsByShortName(source.getName())
                    && !sourceRepo.existsByShortNameAndId(source.getName(),
                    source.getId())) {

                return new Source();
            }

            source.setLastUpdate(LocalDateTime.now());
            sourceRepo.save(source);

            return source;
        }

        return new Source();
    }


}
