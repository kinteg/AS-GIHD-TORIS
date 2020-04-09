package ru.iac.ASGIHDTORIS.spring.controller.logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterSource;
import ru.iac.ASGIHDTORIS.spring.domain.SourceLogger;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterSourceRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceLoggerRepo;

@RestController
@EnableAspectJAutoProxy
@RequestMapping("api/sourceLogger/")
@Slf4j
public class SourceLoggerController {

    private final SourceLoggerRepo sourceLoggerRepo;
    private final BeforeAfterSourceRepo beforeAfterSourceRepo;

    public SourceLoggerController(SourceLoggerRepo sourceLoggerRepo, BeforeAfterSourceRepo beforeAfterSourceRepo) {
        this.sourceLoggerRepo = sourceLoggerRepo;
        this.beforeAfterSourceRepo = beforeAfterSourceRepo;
    }

    @GetMapping("/{id}")
    public SourceLogger getLogger(@PathVariable Long id) {
        return sourceLoggerRepo.findById((long) id);
    }

    @PostMapping("/getAll")
    public Page<SourceLogger> getAllLogger(@PageableDefault Pageable pageable) {
        return sourceLoggerRepo.findAll(pageable);
    }

    @GetMapping("/getAll/{sourceId}")
    public Page<SourceLogger> getAllLogger(@PageableDefault Pageable pageable, @PathVariable Long sourceId) {
        return sourceLoggerRepo.findAllBySourceId(pageable, sourceId);
    }

    @GetMapping("/beforeAfter/{id}")
    public BeforeAfterSource getBeforeAfter(@PathVariable Long id) {
        return beforeAfterSourceRepo.findById((long) id);
    }

    @GetMapping("/getAll/beforeAfter")
    public Page<BeforeAfterSource> getAllBeforeAfter(@PageableDefault Pageable pageable) {
        return beforeAfterSourceRepo.findAll(pageable);
    }

    @GetMapping("/getAll/beforeAfter/{sourceLoggerId}")
    public Page<BeforeAfterSource> getAllBeforeAfter(@PageableDefault Pageable pageable, @PathVariable Long sourceLoggerId) {
        return beforeAfterSourceRepo.findAllBySourceLoggerId(pageable, sourceLoggerId);
    }

}
