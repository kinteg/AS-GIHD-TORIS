package ru.iac.ASGIHDTORIS.spring.component.logger.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.component.logger.error.ErrorCreator;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.ActionsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableLoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.StatusesRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PatternTableLoggerSender implements LoggerSender<PatternTable> {

    private final PatternTableLoggerRepo patternTableLoggerRepo;
    private final ActionsRepo actionsRepo;
    private final StatusesRepo statusesRepo;
    private final ErrorCreator errorCreator;

    public PatternTableLoggerSender(PatternTableLoggerRepo patternTableLoggerRepo, ActionsRepo actionsRepo, StatusesRepo statusesRepo, ErrorCreator errorCreator) {
        this.patternTableLoggerRepo = patternTableLoggerRepo;
        this.actionsRepo = actionsRepo;
        this.statusesRepo = statusesRepo;
        this.errorCreator = errorCreator;
    }

    @Override
    public Long afterCreate(PatternTable object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(10);
        } else {
            status = statusesRepo.findById(5);
        }

        PatternTableLogger patternLogger = PatternTableLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternTableId(object.getId())
                .actions(actionsRepo.findById(4))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return patternTableLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public Long afterArchive(PatternTable object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(8);
        } else {
            status = statusesRepo.findById(3);
        }

        PatternTableLogger patternLogger = PatternTableLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternTableId(object.getId())
                .actions(actionsRepo.findById(2))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return patternTableLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public List<Long> afterArchive(List<PatternTable> object, User user) {
        return object.stream()
                .map(v -> afterArchive(v, user))
                .collect(Collectors.toList());
    }

    @Override
    public Long afterDeArchive(PatternTable object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(9);
        } else {
            status = statusesRepo.findById(4);
        }

        PatternTableLogger patternLogger = PatternTableLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternTableId(object.getId())
                .actions(actionsRepo.findById(3))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return patternTableLoggerRepo.save(patternLogger).getId();
    }

    @Override
    public List<Long> afterDeArchive(List<PatternTable> object, User user) {
        return object.stream()
                .map(v -> afterDeArchive(v, user))
                .collect(Collectors.toList());
    }

    @Override
    public Long afterUpdate(PatternTable object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(11);
        } else {
            status = statusesRepo.findById(6);
        }

        PatternTableLogger patternLogger = PatternTableLogger
                .builder()
                .errors(error)
                .statuses(status)
                .patternTableId(object.getId())
                .actions(actionsRepo.findById(5))
                .dateCreation(LocalDateTime.now())
                .build();

        return patternTableLoggerRepo.save(patternLogger).getId();
    }

}
