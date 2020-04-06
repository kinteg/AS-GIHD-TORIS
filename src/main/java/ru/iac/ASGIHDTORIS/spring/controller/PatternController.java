package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo2;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class PatternController {

    private final Validator<Pattern> patternValidator;
    private final PatternRepo patternRepo;
    private final PatternRepo2 patternRepo2;
    private final LoggerSender<Pattern> patternLoggerSender;
    private final BeforeAfter<Pattern> patternBeforeAfter;

    public PatternController(
            @Qualifier("getPatternValidator") Validator<Pattern> patternValidator,
            PatternRepo patternRepo,
            PatternRepo2 patternRepo2,
            @Qualifier("patternLoggerSender") LoggerSender<Pattern> patternLoggerSender,
            @Qualifier("patternBeforeAfter") BeforeAfter<Pattern> patternBeforeAfter) {

        this.patternValidator = patternValidator;
        this.patternRepo = patternRepo;
        this.patternRepo2 = patternRepo2;
        this.patternLoggerSender = patternLoggerSender;
        this.patternBeforeAfter = patternBeforeAfter;
    }

    @PostMapping("/create")
    public Pattern createPattern(@ModelAttribute Pattern pattern) {

        pattern.setDateCreation(LocalDateTime.now());
        pattern.setDateActivation(LocalDateTime.now());
        pattern.setLastUpdate(LocalDateTime.now());
        pattern.setArchiveFileCount(0);
        pattern.setFileCount(0);
        pattern.setIsArchive(false);

        Pattern patternAfter = patternValidator.isValid(pattern)
                ? patternRepo.save(pattern)
                : Pattern.builder().id(Long.parseLong("-1")).build();


        long loggerId = patternLoggerSender.afterCreate(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterCreate(patternAfter, loggerId);
        }

        return patternAfter;
    }

    @GetMapping("/{id}")
    public Pattern getById(@PathVariable Long id) {
        return patternRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<Pattern> getAll(@PageableDefault Pageable pageable) {
        return patternRepo.findAll(pageable);
    }

    @PostMapping("/getAllSort")
    public Page<Pattern> getAll(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        log.info(pattern.toString());
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAll/{sourceId}")
    public Page<Pattern> getAll(@PathVariable Long sourceId, @PageableDefault Pageable pageable) {
        return patternRepo.findAllBySourceId(sourceId, pageable);
    }

    @PostMapping("/getAllSort/{sourceId}")
    public Page<Pattern> getAllWithSourceId(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive")
    public Page<Pattern> getAllArchive(@PageableDefault Pageable pageable) {
        return patternRepo.findAllByIsArchive(true, pageable);
    }

    @PostMapping("/getAllArchiveSort")
    public Page<Pattern> getAllArchive(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllArchive/{sourceId}")
    public Page<Pattern> getAllArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, true, pageable);
    }

    @PostMapping("/getAllArchiveSort/{sourceId}")
    public Page<Pattern> getAllArchiveWithSourceId(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(true);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive")
    public Page<Pattern> getAllNotArchive(@PageableDefault Pageable pageable) {
        return patternRepo.findAllByIsArchive(false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort")
    public Page<Pattern> getAllNotArchive(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/getAllNotArchive/{sourceId}")
    public Page<Pattern> getAllNotArchive(@PathVariable Long sourceId, @PageableDefault Pageable pageable) {
        return patternRepo.findAllBySourceIdAndIsArchive(sourceId, false, pageable);
    }

    @PostMapping("/getAllNotArchiveSort/{sourceId}")
    public Page<Pattern> getAllNotArchiveWithSourceId(@ModelAttribute PatternModel pattern, @PageableDefault Pageable pageable, @ModelAttribute HelpModel helpModel) {
        pattern.setHelpModel(helpModel);
        pattern.getHelpModel().setIsArchive(false);
        return patternRepo2.findAllSourceByQuery(pageable, pattern);
    }

    @GetMapping("/archive/{id}")
    public Pattern archivePattern(@PathVariable Long id) {
        Pattern patternBefore, patternAfter;
        if (id == null) {
            patternAfter = Pattern.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternRepo.existsById(id)) {
            patternAfter =  Pattern.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternRepo.findById((long) id);
            patternBefore = Pattern
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();

            patternAfter.setIsArchive(true);
            patternAfter.setDateDeactivation(LocalDateTime.now());

            patternRepo.save(patternAfter);

        }

        long loggerId = patternLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;

    }

    @GetMapping("/deArchive/{id}")
    public Pattern deArchivePattern(@PathVariable Long id) {
        Pattern patternBefore, patternAfter;

        if (id == null) {
            patternAfter = Pattern.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternRepo.existsById(id)) {
            patternAfter =  Pattern.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternRepo.findById((long) id);
            patternBefore = Pattern
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateActivation(patternAfter.getDateDeactivation())
                    .build();

            patternAfter.setIsArchive(false);
            patternAfter.setDateActivation(LocalDateTime.now());

            patternRepo.save(patternAfter);

        }

        long loggerId = patternLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterDeArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
    }


    @GetMapping("/archivePatterns/{sourceId}")
    public List<Pattern> archivePatterns(@PathVariable Long sourceId) {

        if (sourceId == null) {
            return Collections.singletonList(Pattern.builder().id((long) -4).build());

        } else if (!patternRepo.existsBySourceId(sourceId)) {
            return Collections.singletonList(Pattern.builder().id((long) -3).build());

        } else {
            List<Pattern> patterns = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            return patternRepo.saveAll(patterns);
        }

    }

    @GetMapping("/deArchivePatterns/{sourceId}")
    public List<Pattern> deArchivePatterns(@PathVariable Long sourceId) {

        if (sourceId == null) {
            return Collections.singletonList(Pattern.builder().id((long) -4).build());

        } else if (!patternRepo.existsBySourceId(sourceId)) {
            return Collections.singletonList(Pattern.builder().id((long) -3).build());

        } else {

            List<Pattern> patterns = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            return patternRepo.saveAll(patterns);
        }
    }

    @PostMapping("/update")
    public Pattern update(@ModelAttribute Pattern pattern) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (pattern.getId() == null) {
            afterUpdate = Pattern.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(pattern.getId())) {
            afterUpdate = Pattern.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else if (!patternValidator.isValid(pattern)) {
            afterUpdate = Pattern.builder().id((long) -1).build();
            beforeUpdate = afterUpdate;
        } else {

            beforeUpdate = new Pattern(patternRepo.findById((long) pattern.getId()));

            pattern.setLastUpdate(LocalDateTime.now());
            pattern.setDateCreation(beforeUpdate.getDateCreation());
            pattern.setDateActivation(beforeUpdate.getDateActivation());
            pattern.setDateDeactivation(beforeUpdate.getDateDeactivation());

            afterUpdate = patternRepo.save(pattern);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }

        return afterUpdate;
    }

}
