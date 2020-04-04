package ru.iac.ASGIHDTORIS.spring.controller.aspect.logger;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.ActionsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.ErrorsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.LoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.StatusesRepo;

import java.time.LocalDateTime;

@Component
public class LoggerSenderImpl implements LoggerSender {

    private final LoggerRepo loggerRepo;
    private final ActionsRepo actionsRepo;
    private final ErrorsRepo errorsRepo;
    private final StatusesRepo statusesRepo;

    public LoggerSenderImpl(LoggerRepo loggerRepo, ActionsRepo actionsRepo, ErrorsRepo errorsRepo, StatusesRepo statusesRepo) {
        this.loggerRepo = loggerRepo;
        this.actionsRepo = actionsRepo;
        this.errorsRepo = errorsRepo;
        this.statusesRepo = statusesRepo;
    }

    @Override
    public void afterReturningCreate(Long id, Objects object) {
        Errors error = errorCreator(id);
        Statuses status;

        if (id == null || id < 0) {
            status = statusesRepo.findById(10);
        } else {
            status = statusesRepo.findById(5);
        }

        Logger logger = Logger
                .builder()
                .errors(error)
                .statuses(status)
                .objects(object)
                .actions(actionsRepo.findById(4))
                .dateCreation(LocalDateTime.now())
                .build();

        loggerRepo.save(logger);
    }

    @Override
    public void afterReturningArchive(Long id, Objects object) {
        Errors error = errorCreator(id);
        Statuses status;

        if (id == null || id < 0) {
            status = statusesRepo.findById(8);
        } else {
            status = statusesRepo.findById(3);
        }

        Logger logger = Logger
                .builder()
                .errors(error)
                .statuses(status)
                .objects(object)
                .actions(actionsRepo.findById(2))
                .dateCreation(LocalDateTime.now())
                .build();

        loggerRepo.save(logger);
    }

    @Override
    public void afterReturningDeArchive(Long id, Objects object) {
        Errors error = errorCreator(id);
        Statuses status;

        if (id == null || id < 0) {
            status = statusesRepo.findById(9);
        } else {
            status = statusesRepo.findById(4);
        }

        Logger logger = Logger
                .builder()
                .errors(error)
                .statuses(status)
                .objects(object)
                .actions(actionsRepo.findById(3))
                .dateCreation(LocalDateTime.now())
                .build();

        loggerRepo.save(logger);
    }

    @Override
    public void afterReturningUpdate(Long id, Objects object) {
        Errors error = errorCreator(id);
        Statuses status;

        if (id == null || id < 0) {
            status = statusesRepo.findById(11);
        } else {
            status = statusesRepo.findById(6);
        }

        Logger logger = Logger
                .builder()
                .errors(error)
                .statuses(status)
                .objects(object)
                .actions(actionsRepo.findById(5))
                .dateCreation(LocalDateTime.now())
                .build();

        loggerRepo.save(logger);
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
