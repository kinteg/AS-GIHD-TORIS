package ru.iac.ASGIHDTORIS.spring.controller.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternLogger;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternLoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableLoggerRepo;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/patternTableLogger/")
@Slf4j
public class PatternTableLoggerController {

    private final PatternLoggerRepo patternLoggerRepo;
    private final BeforeAfterPatternRepo beforeAfterPatternRepo;
    private final PatternTableLoggerRepo patternTableLoggerRepo;
    public PatternTableLoggerController(PatternLoggerRepo patternLoggerRepo, BeforeAfterPatternRepo beforeAfterPatternRepo, PatternTableLoggerRepo patternTableLoggerRepo) {
        this.patternLoggerRepo = patternLoggerRepo;
        this.beforeAfterPatternRepo = beforeAfterPatternRepo;
        this.patternTableLoggerRepo = patternTableLoggerRepo;
    }

    @GetMapping("/{id}")
    public PatternLogger getLogger(@PathVariable Long id) {
        return patternLoggerRepo.findById((long) id);
    }

    @PostMapping("/getAll")
    public Page<PatternLogger> getAllLogger(@PageableDefault Pageable pageable) {
        return patternTableLoggerRepo.findAll(pageable);
    }

    @GetMapping("/getAll/{patternTableId}")
    public Page<PatternLogger> getAllLogger(@PageableDefault Pageable pageable, @PathVariable Long patternTableId) {
        return patternLoggerRepo.findAllByPatternId(pageable, patternTableId);
    }

    @GetMapping("/beforeAfter/{id}")
    public BeforeAfterPattern getBeforeAfter(@PathVariable Long id) {
        return beforeAfterPatternRepo.findById((long) id);
    }

    @GetMapping("/getAll/beforeAfter")
    public Page<BeforeAfterPattern> getAllBeforeAfter(@PageableDefault Pageable pageable) {
        return beforeAfterPatternRepo.findAll(pageable);
    }

    @GetMapping("/getAll/beforeAfter/{patternTableLoggerId}")
    public Page<BeforeAfterPattern> getAllBeforeAfter(@PageableDefault Pageable pageable, @PathVariable Long patternTableLoggerId) {
        return beforeAfterPatternRepo.findAllByPatternLoggerId(pageable, patternTableLoggerId);
    }

}
