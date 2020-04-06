package ru.iac.ASGIHDTORIS.spring.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SourceControllerAspect {

//    private final LoggerSender<Source> loggerSender;
//    private final BeforeAfter<Source> beforeAfter;
//
//    public SourceControllerAspect(
//            @Qualifier("sourceLoggerSender") LoggerSender<Source> loggerSender,
//            @Qualifier("sourceBeforeAfter") BeforeAfter<Source> beforeAfter) {
//
//        this.loggerSender = loggerSender;
//        this.beforeAfter = beforeAfter;
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.createSource(..)) && args(source)")
//    public void callCreateSourceAfterReturning(Source source) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.archiveSource(..)) && args(id)")
//    public void callArchiveSourceAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.deArchiveSource(..)) && args(id)")
//    public void callDeArchiveSourceAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.updateSource(..)) && args(source)")
//    public void callUpdateSourceAfterReturning(Source source) {
//    }
//
//    @AfterReturning(pointcut = "callCreateSourceAfterReturning(source)", returning = "sourceAfter", argNames = "source,sourceAfter")
//    public void afterReturningCreateSource(Source source, Source sourceAfter) {
//        Long id = loggerSender.afterCreate(sourceAfter.getId(), sourceAfter);
//        if (sourceAfter.getId() > 0) {
//            beforeAfter.afterReturningCreate(source, sourceAfter, id);
//        }
//    }
//
//    @AfterReturning(pointcut = "callArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
//    public void afterReturningArchiveSource(Source source, Long id) {
//        Long loggerId = loggerSender.afterReturningArchive(id, source);
//        if (source.getId() > 0) {
//            beforeAfter.afterReturningArchive(source, loggerId);
//        }
//    }
//
//    @AfterReturning(pointcut = "callDeArchiveSourceAfterReturning(id)", returning = "source", argNames = "source,id")
//    public void afterReturningDeArchiveSource(Source source, Long id) {
//        Long loggerId = loggerSender.afterReturningDeArchive(id, source);
//        if (source.getId() > 0) {
//            beforeAfter.afterReturningDeArchive(source, loggerId);
//        }
//    }
//
//    @AfterReturning(pointcut = "callUpdateSourceAfterReturning(source)", returning = "sourceAfter", argNames = "source,sourceAfter")
//    public void afterReturningUpdateSource(Source source, Source sourceAfter) {
//        Long id = loggerSender.afterReturningUpdate(sourceAfter.getId(), sourceAfter);
//        if (sourceAfter.getId() > 0) {
//            System.out.println(source.getName());
//            System.out.println(sourceAfter.getName());
//            beforeAfter.afterReturningUpdate(source, sourceAfter, id);
//        }
//    }

}
