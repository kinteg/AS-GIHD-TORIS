package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.iac.ASGIHDTORIS.domain.Source;
import ru.iac.ASGIHDTORIS.repo.SourceRepo;

@RestController
@RequestMapping("api/")
@Slf4j
public class SourceController {

    private final SourceRepo sourceRepo;

    public SourceController(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @PostMapping("/create")
    public Source createSource (@RequestBody Source name){
        log.info(name.toString());
        return sourceRepo.save(name);
    }
}
