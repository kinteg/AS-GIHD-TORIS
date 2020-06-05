package ru.iac.ASGIHDTORIS.spring.component.logger.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.component.logger.error.ErrorCreator;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.ActionsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternLoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.StatusesRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatternLoggerSender implements LoggerSender<Pattern> {

    private final PatternLoggerRepo patternLoggerRepo;
    private final ActionsRepo actionsRepo;
    private final StatusesRepo statusesRepo;
    private final ErrorCreator errorCreator;

    public PatternLoggerSender(PatternLoggerRepo patternLoggerRepo, ActionsRepo actionsRepo, StatusesRepo statusesRepo, ErrorCreator errorCreator) {
        this.patternLoggerRepo = patternLoggerRepo;
        this.actionsRepo = actionsRepo;
        this.statusesRepo = statusesRepo;
        this.errorCreator = errorCreator;
    }

    @Override
    public Long afterCreate(Pattern object) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(10);
        } else {
            status = statusesRepo.findById(5);
        }

        PatternLogger patternLogger = PatternLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternId(object.getId())
                .actions(actionsRepo.findById(4))
                .dateCreation(LocalDateTime.now())
                .build();

        return patternLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public Long afterArchive(Pattern object) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(8);
        } else {
            status = statusesRepo.findById(3);
        }

        PatternLogger patternLogger = PatternLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternId(object.getId())
                .actions(actionsRepo.findById(2))
                .dateCreation(LocalDateTime.now())
                .build();

        return patternLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public List<Long> afterArchive(List<Pattern> object) {
        return object.stream()
                .map(this::afterArchive)
                .collect(Collectors.toList());
    }

    @Override
    public Long afterDeArchive(Pattern object) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(9);
        } else {
            status = statusesRepo.findById(4);
        }

        PatternLogger patternLogger = PatternLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternId(object.getId())
                .actions(actionsRepo.findById(3))
                .dateCreation(LocalDateTime.now())
                .build();

        return patternLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public List<Long> afterDeArchive(List<Pattern> object) {
        return object.stream()
                .map(this::afterDeArchive)
                .collect(Collectors.toList());
    }

    @Override
    public Long afterUpdate(Pattern object) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(11);
        } else {
            status = statusesRepo.findById(6);
        }

        PatternLogger patternLogger = PatternLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternId(object.getId())
                .actions(actionsRepo.findById(5))
                .dateCreation(LocalDateTime.now())
                .build();

        return patternLoggerRepo.save(patternLogger).getId();
    }

}
