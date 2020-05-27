package ru.iac.ASGIHDTORIS.spring.component.logger.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.component.logger.error.ErrorCreator;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.ActionsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceLoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.StatusesRepo;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SourceLoggerSender implements LoggerSender<Source> {

    private final SourceLoggerRepo sourceLoggerRepo;
    private final ActionsRepo actionsRepo;
    private final StatusesRepo statusesRepo;
    private final ErrorCreator errorCreator;

    public SourceLoggerSender(SourceLoggerRepo sourceLoggerRepo, ActionsRepo actionsRepo, StatusesRepo statusesRepo, ErrorCreator errorCreator) {
        this.sourceLoggerRepo = sourceLoggerRepo;
        this.actionsRepo = actionsRepo;
        this.statusesRepo = statusesRepo;
        this.errorCreator = errorCreator;
    }

    @Override
    public Long afterCreate(Source object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(10);
        } else {
            status = statusesRepo.findById(5);
        }

        SourceLogger sourceLogger = SourceLogger
                .builder()
                .errors(error)
                .statuses(status)
                .sourceId(object.getId())
                .actions(actionsRepo.findById(4))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public Long afterArchive(Source object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(8);
        } else {
            status = statusesRepo.findById(3);
        }

        SourceLogger sourceLogger = SourceLogger
                .builder()
                .errors(error)
                .statuses(status)
                .sourceId(object.getId())
                .actions(actionsRepo.findById(2))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public List<Long> afterArchive(List<Source> object, User user) {
        return object.stream()
                .map(v -> afterArchive(v, user))
                .collect(Collectors.toList());
    }

    @Override
    public Long afterDeArchive(Source object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(9);
        } else {
            status = statusesRepo.findById(4);
        }

        SourceLogger sourceLogger = SourceLogger
                .builder()
                .errors(error)
                .statuses(status)
                .sourceId(object.getId())
                .actions(actionsRepo.findById(3))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public List<Long> afterDeArchive(List<Source> object, User user) {
        return object.stream()
                .map(v -> afterDeArchive(v, user))
                .collect(Collectors.toList());
    }

    @Override
    public Long afterUpdate(Source object, User user) {
        Errors error = errorCreator.errorCreator(object.getId());
        Statuses status;

        if (object.getId() == null || object.getId() < 0) {
            status = statusesRepo.findById(11);
        } else {
            status = statusesRepo.findById(6);
        }

        SourceLogger sourceLogger = SourceLogger
                .builder()
                .errors(error)
                .statuses(status)
                .sourceId(object.getId())
                .actions(actionsRepo.findById(5))
                .dateCreation(LocalDateTime.now())
                .usrId(user)
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

}
