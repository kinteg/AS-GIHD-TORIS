package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo2;

import java.time.LocalDateTime;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/source/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;
    private final Validator<Source> validator;
    private final SourceRepo2 sourceRepo2;

    public SourceController(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator,
            SourceRepo2 sourceRepo2) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
        this.sourceRepo2 = sourceRepo2;
    }

    @PostMapping("/create")
    @ResponseBody
    public Source createSource(@ModelAttribute Source source) {

        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        return validator.isValid(source)
                ? !sourceRepo.existsByShortName(source.getShortName())
                ? sourceRepo.save(source)
                : Source.builder().id(Long.parseLong("-2")).build()
                : Source.builder().id(Long.parseLong("-1")).build();
    }

    @GetMapping("/checkName")
    public boolean checkName(String name) {
        return sourceRepo.existsByShortName(name);
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
    public Page<Source> getAll(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Source> getAllArchive(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(true);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Source> getAllNotArchive(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(false);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable Long id) {
        if (id == null) {
            return Source.builder().id((long) -4).build();

        } else if (!sourceRepo.existsById(id)) {
            return Source.builder().id((long) -3).build();

        } else {
            Source source = sourceRepo.findById((long) id);
            source.setIsArchive(true);
            source.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }

    }

    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable Long id) {
        if (id == null) {
            return Source.builder().id((long) -4).build();

        } else if (!sourceRepo.existsById(id)) {
            return Source.builder().id((long) -3).build();

        } else {
            Source source = sourceRepo.findById((long) id);
            source.setIsArchive(false);
            source.setDateActivation(LocalDateTime.now());

            sourceRepo.save(source);

            return source;
        }
    }

    @PostMapping("/update")
    @ResponseBody
    public Source updateSource(@ModelAttribute Source source) {
        if (source.getId() == null) {
            return Source.builder().id((long) -4).build();

        } else if (!sourceRepo.existsById(source.getId())) {
            return Source.builder().id((long) -3).build();

        } else if (!validator.isValid(source)) {
            return Source.builder().id((long) -1).build();

        } else {
            if (sourceRepo.existsByShortName(source.getShortName())
                    && !sourceRepo.existsByShortNameAndId(
                    source.getShortName(),
                    source.getId())) {

                return Source.builder().id((long) -2).build();
            }

            Source existPattern = sourceRepo.findById((long) source.getId());

            source.setLastUpdate(LocalDateTime.now());
            source.setDateCreation(existPattern.getDateCreation());
            source.setDateActivation(existPattern.getDateActivation());
            source.setDateDeactivation(existPattern.getDateDeactivation());

            return sourceRepo.save(source);
        }
    }

}
