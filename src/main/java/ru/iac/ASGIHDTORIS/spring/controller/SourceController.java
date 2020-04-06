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
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
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
    private final LoggerSender<Source> sourceLoggerSender;
    private final BeforeAfter<Source> sourceBeforeAfter;

    public SourceController(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator,
            SourceRepo2 sourceRepo2,
            @Qualifier("sourceLoggerSender") LoggerSender<Source> sourceLoggerSender,
            @Qualifier("sourceBeforeAfter") BeforeAfter<Source> sourceBeforeAfter) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
        this.sourceRepo2 = sourceRepo2;
        this.sourceLoggerSender = sourceLoggerSender;
        this.sourceBeforeAfter = sourceBeforeAfter;
    }

    @PostMapping("/create")
    @ResponseBody
    public Source createSource(@ModelAttribute Source source) {

        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        Source sourceAfter = validator.isValid(source)
                ? !sourceRepo.existsByShortName(source.getShortName())
                ? sourceRepo.save(source)
                : Source.builder().id(Long.parseLong("-2")).build()
                : Source.builder().id(Long.parseLong("-1")).build();

        long loggerId = sourceLoggerSender.afterCreate(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterCreate(sourceAfter, loggerId);
        }

        return sourceAfter;
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
    public Page<Source> getAllSort(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllArchive")
    public Page<Source> getAllArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Source> getAllArchiveSort(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(true);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Source> getAllNotArchive(@PageableDefault Pageable pageable) {
        return sourceRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Source> getAllNotArchiveSort(@ModelAttribute SourceModel source, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        source.setHelpModel(helpModel);
        source.getHelpModel().setIsArchive(false);
        return sourceRepo2.findAllSourceByQuery(pageable, source);
    }

    @GetMapping("/archive/{id}")
    public Source archiveSource(@PathVariable Long id) {
        Source sourceBefore, sourceAfter;

        if (id == null) {
            sourceAfter = Source.builder().id((long) -4).build();
            sourceBefore = sourceAfter;
        } else if (!sourceRepo.existsById(id)) {
            sourceAfter = Source.builder().id((long) -3).build();
            sourceBefore = sourceAfter;
        } else {
            sourceAfter = sourceRepo.findById((long) id);
            sourceBefore = Source
                    .builder()
                    .isArchive(sourceAfter.getIsArchive())
                    .dateDeactivation(sourceAfter.getDateDeactivation())
                    .build();
            sourceAfter.setIsArchive(true);
            sourceAfter.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(sourceAfter);

        }

        long loggerId = sourceLoggerSender.afterArchive(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterArchive(sourceBefore, sourceAfter, loggerId);
        }

        return sourceAfter;

    }

    @GetMapping("/deArchive/{id}")
    public Source deArchiveSource(@PathVariable Long id) {
        Source sourceBefore, sourceAfter;

        if (id == null) {
            sourceAfter = Source.builder().id((long) -4).build();
            sourceBefore = sourceAfter;
        } else if (!sourceRepo.existsById(id)) {
            sourceAfter = Source.builder().id((long) -3).build();
            sourceBefore = sourceAfter;
        } else {
            sourceAfter = sourceRepo.findById((long) id);
            sourceBefore = Source
                    .builder()
                    .isArchive(sourceAfter.getIsArchive())
                    .dateActivation(sourceAfter.getDateActivation())
                    .build();

            sourceAfter.setIsArchive(false);
            sourceAfter.setDateActivation(LocalDateTime.now());

            sourceRepo.save(sourceAfter);

        }

        long loggerId = sourceLoggerSender.afterDeArchive(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterDeArchive(sourceBefore, sourceAfter, loggerId);
        }

        return sourceAfter;
    }

    @PostMapping("/update")
    @ResponseBody
    public Source updateSource(@ModelAttribute Source source) {
        Source afterUpdate;
        Source beforeUpdate;

        if (source.getId() == null) {
            afterUpdate = Source.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!sourceRepo.existsById(source.getId())) {
            afterUpdate = Source.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else if (!validator.isValid(source)) {
            afterUpdate = Source.builder().id((long) -1).build();
            beforeUpdate = afterUpdate;
        } else if (sourceRepo.existsByShortName(source.getShortName())
                && !sourceRepo.existsByShortNameAndId(
                source.getShortName(),
                source.getId())) {

            afterUpdate = Source.builder().id((long) -2).build();
            beforeUpdate = afterUpdate;
        } else {

            beforeUpdate = new Source(sourceRepo.findById((long) source.getId()));

            source.setLastUpdate(LocalDateTime.now());
            source.setDateCreation(beforeUpdate.getDateCreation());
            source.setDateActivation(beforeUpdate.getDateActivation());
            source.setDateDeactivation(beforeUpdate.getDateDeactivation());

            afterUpdate = sourceRepo.save(source);
        }

        long loggerId = sourceLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            sourceBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }

        return afterUpdate;
    }

}
