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
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableLogger;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableLoggerRepo;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/patternTableLogger/")
@Slf4j
public class PatternTableLoggerController {

    private final PatternTableLoggerRepo patternLoggerRepo;
    private final BeforeAfterPatternTableRepo beforeAfterPatternRepo;

    public PatternTableLoggerController(PatternTableLoggerRepo patternLoggerRepo, BeforeAfterPatternTableRepo beforeAfterPatternRepo) {
        this.patternLoggerRepo = patternLoggerRepo;
        this.beforeAfterPatternRepo = beforeAfterPatternRepo;
    }

    @GetMapping("/{id}")
    public PatternTableLogger getLogger(@PathVariable Long id) {
        return patternLoggerRepo.findById((long) id);
    }

    @GetMapping("/getAll")
    public Page<PatternTableLogger> getAllLogger(@PageableDefault Pageable pageable) {
        return patternLoggerRepo.findAll(pageable);
    }

    @GetMapping("/getAll/{patternTableId}")
    public Page<PatternTableLogger> getAllLogger(@PageableDefault Pageable pageable, @PathVariable Long patternTableId) {
        return patternLoggerRepo.findAllByPatternTableId(pageable, patternTableId);
    }

    @GetMapping("/beforeAfter/{id}")
    public BeforeAfterPatternTable getBeforeAfter(@PathVariable Long id) {
        return beforeAfterPatternRepo.findById((long) id);
    }

    @GetMapping("/getAll/beforeAfter")
    public Page<BeforeAfterPatternTable> getAllBeforeAfter(@PageableDefault Pageable pageable) {
        return beforeAfterPatternRepo.findAll(pageable);
    }

    @GetMapping("/getAll/beforeAfter/{patternTableLoggerId}")
    public Page<BeforeAfterPatternTable> getAllBeforeAfter(@PageableDefault Pageable pageable, @PathVariable Long patternTableLoggerId) {
        return beforeAfterPatternRepo.findAllByPatternTableLoggerId(pageable, patternTableLoggerId);
    }

}
