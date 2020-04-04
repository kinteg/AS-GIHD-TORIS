package ru.iac.ASGIHDTORIS.spring.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.*;

import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class SourceControllerAspect {

    private final Objects object;

    private final LoggerRepo loggerRepo;
    private final ActionsRepo actionsRepo;
    private final ErrorsRepo errorsRepo;
    private final StatusesRepo statusesRepo;

    public SourceControllerAspect(LoggerRepo loggerRepo, ActionsRepo actionsRepo, ErrorsRepo errorsRepo, ObjectsRepo objectsRepo, StatusesRepo statusesRepo) {
        this.loggerRepo = loggerRepo;
        this.actionsRepo = actionsRepo;
        this.errorsRepo = errorsRepo;
        this.statusesRepo = statusesRepo;
        object = objectsRepo.findById(1);
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.createSource(..)) && args(source)")
    public void callCreateSourceAfterReturning(Source source) {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.archiveSource(..)) && args(id)")
    public void callArchiveSourceAfterReturning(Long id) {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.deArchiveSource(..)) && args(id)")
    public void callDeArchiveSourceAfterReturning(Long id) {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.updateSource(..)) && args(source)")
    public void callUpdateSourceAfterReturning(Source source) {
    }

    @AfterReturning(pointcut = "callCreateSourceAfterReturning(source)", returning = "sourceAfter", argNames = "source,sourceAfter")
    public void afterReturningCreateSource(Source source, Source sourceAfter) {
        Errors error = errorCreator(sourceAfter.getId());
        Statuses status;

        if (sourceAfter.getId() == null || sourceAfter.getId() < 0) {
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

    @AfterReturning(pointcut = "callArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
    public void afterReturningArchiveSource(Source source, Long id) {
        Errors error = errorCreator(source.getId());
        Statuses status;

        if (source.getId() == null || source.getId() < 0) {
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

    @AfterReturning(pointcut = "callDeArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
    public void afterReturningDeArchiveSource(Source source, Long id) {
        Errors error = errorCreator(source.getId());
        Statuses status;

        if (source.getId() == null || source.getId() < 0) {
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

    @AfterReturning(pointcut = "callUpdateSourceAfterReturning(source)", returning = "sourceAfter", argNames = "source,sourceAfter")
    public void afterReturningUpdateSource(Source source, Source sourceAfter) {
        Errors error = errorCreator(sourceAfter.getId());
        Statuses status;

        if (sourceAfter.getId() == null || sourceAfter.getId() < 0) {
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
