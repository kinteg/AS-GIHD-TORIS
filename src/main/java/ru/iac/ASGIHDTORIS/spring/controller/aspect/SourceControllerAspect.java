package ru.iac.ASGIHDTORIS.spring.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.controller.aspect.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.*;
import ru.iac.ASGIHDTORIS.spring.repo.*;

@Aspect
@Component
@Slf4j
public class SourceControllerAspect {

    private final Objects object;
    private final LoggerSender loggerSender;

    public SourceControllerAspect(ObjectsRepo objectsRepo, LoggerSender loggerSender) {
        object = objectsRepo.findById(1);
        this.loggerSender = loggerSender;
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
        loggerSender.afterReturningCreate(sourceAfter.getId(), object);
    }

    @AfterReturning(pointcut = "callArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
    public void afterReturningArchiveSource(Source source, Long id) {
        loggerSender.afterReturningArchive(id, object);
    }

    @AfterReturning(pointcut = "callDeArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
    public void afterReturningDeArchiveSource(Source source, Long id) {
        loggerSender.afterReturningDeArchive(id, object);
    }

    @AfterReturning(pointcut = "callUpdateSourceAfterReturning(source)", returning = "sourceAfter", argNames = "source,sourceAfter")
    public void afterReturningUpdateSource(Source source, Source sourceAfter) {
        loggerSender.afterReturningUpdate(sourceAfter.getId(), object);
    }

}
