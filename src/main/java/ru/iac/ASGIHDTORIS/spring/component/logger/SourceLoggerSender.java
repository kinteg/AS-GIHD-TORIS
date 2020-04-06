package ru.iac.ASGIHDTORIS.spring.component.logger;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.*;

import java.time.LocalDateTime;

@Component
public class SourceLoggerSender implements LoggerSender<Source> {

    private final SourceLoggerRepo sourceLoggerRepo;
    private final ActionsRepo actionsRepo;
    private final ErrorsRepo errorsRepo;
    private final StatusesRepo statusesRepo;

    public SourceLoggerSender(SourceLoggerRepo sourceLoggerRepo, ActionsRepo actionsRepo, ErrorsRepo errorsRepo, StatusesRepo statusesRepo) {
        this.sourceLoggerRepo = sourceLoggerRepo;
        this.actionsRepo = actionsRepo;
        this.errorsRepo = errorsRepo;
        this.statusesRepo = statusesRepo;
    }

    @Override
    public Long afterCreate(Source object) {
        Errors error = errorCreator(object.getId());
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
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public Long afterArchive(Source object) {
        Errors error = errorCreator(object.getId());
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
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public Long afterDeArchive(Source object) {
        Errors error = errorCreator(object.getId());
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
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }

    @Override
    public Long afterUpdate(Source object) {
        Errors error = errorCreator(object.getId());
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
                .build();

        return sourceLoggerRepo.save(sourceLogger).getId();
    }


    private Errors errorCreator(Long id) {
        Errors error;

        if (id == null) {
            error = errorsRepo.findById(5);

        } else if (id == -1) {
            error = errorsRepo.findById(2);

        } else if (id == -2) {
            error = errorsRepo.findById(3);

        } else if (id == -3) {
            error = errorsRepo.findById(4);

        } else {
            error = errorsRepo.findById(1);
        }

        return error;
    }

}
