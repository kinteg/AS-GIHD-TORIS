package ru.iac.ASGIHDTORIS.spring.controller.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternLogger;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternLoggerRepo;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/patternLogger/")
@Slf4j
public class PatternLoggerController {

    private final PatternLoggerRepo patternLoggerRepo;
    private final BeforeAfterPatternRepo beforeAfterPatternRepo;

    public PatternLoggerController(PatternLoggerRepo patternLoggerRepo, BeforeAfterPatternRepo beforeAfterPatternRepo) {
        this.patternLoggerRepo = patternLoggerRepo;
        this.beforeAfterPatternRepo = beforeAfterPatternRepo;
    }

    @GetMapping("/{id}")
    public PatternLogger getLogger(@PathVariable Long id) {
        return patternLoggerRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<PatternLogger> getAllLogger(@PageableDefault Pageable pageable) {
        return patternLoggerRepo.findAll(pageable);
    }

    @GetMapping("/getAll/{sourceId}")
    public Page<PatternLogger> getAllLogger(@PageableDefault Pageable pageable, @PathVariable Long sourceId) {
        return patternLoggerRepo.findAllByPatternId(pageable, sourceId);
    }

    @GetMapping("/beforeAfter/{id}")
    public BeforeAfterPattern getBeforeAfter(@PathVariable Long id) {
        return beforeAfterPatternRepo.findById((long) id);
    }

    @GetMapping("/getAll/beforeAfter")
    public Page<BeforeAfterPattern> getAllBeforeAfter(@PageableDefault Pageable pageable) {
        return beforeAfterPatternRepo.findAll(pageable);
    }

    @GetMapping("/getAll/beforeAfter/{sourceLoggerId}")
    public Page<BeforeAfterPattern> getAllBeforeAfter(@PageableDefault Pageable pageable, @PathVariable Long sourceLoggerId) {
        return beforeAfterPatternRepo.findAllByPatternLoggerId(pageable, sourceLoggerId);
    }

}
