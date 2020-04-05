package ru.iac.ASGIHDTORIS.spring.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.controller.aspect.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Objects;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.ObjectsRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.StatusesRepo;

import java.util.List;

@Aspect
@Component
@Slf4j
public class PatternControllerAspect {

//    private final Objects object;
//    private final LoggerSender loggerSender;
//    private final PatternRepo patternRepo;
//
//    public PatternControllerAspect(ObjectsRepo objectsRepo, LoggerSender loggerSender, PatternRepo patternRepo) {
//        object = objectsRepo.findById(2);
//        this.loggerSender = loggerSender;
//        this.patternRepo = patternRepo;
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.createPattern(..)) && args(pattern)")
//    public void callCreatePatternAfterReturning(Pattern pattern) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.archivePattern(..)) && args(id)")
//    public void callArchivePatternAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.deArchivePattern(..)) && args(id)")
//    public void callDeArchivePatternAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.archivePatterns(..)) && args(id)")
//    public void callArchivePatternsAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.deArchivePatterns(..)) && args(id)")
//    public void callDeArchivePatternsAfterReturning(Long id) {
//    }
//
//    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.update(..)) && args(pattern)")
//    public void callUpdatePatternAfterReturning(Pattern pattern) {
//    }
//
//    @AfterReturning(pointcut = "callCreatePatternAfterReturning(pattern)", returning = "patternAfter", argNames = "pattern,patternAfter")
//    public void afterReturningCreatePattern(Pattern pattern, Pattern patternAfter) {
//        loggerSender.afterReturningCreate(patternAfter.getId(), object);
//    }
//
//    @AfterReturning(pointcut = "callArchivePatternAfterReturning(id)", returning = "pattern", argNames = "pattern,id")
//    public void afterReturningArchivePattern(Pattern pattern, Long id) {
//        loggerSender.afterReturningArchive(id, object);
//    }
//
//    @AfterReturning(pointcut = "callDeArchivePatternAfterReturning(id)", returning = "pattern", argNames = "pattern,id")
//    public void afterReturningDeArchivePattern(Pattern pattern, Long id) {
//        loggerSender.afterReturningDeArchive(id, object);
//    }
//
//    @AfterReturning(pointcut = "callArchivePatternsAfterReturning(id)", returning = "patterns", argNames = "patterns,id")
//    public void afterReturningArchivePatterns(List<Pattern> patterns, Long id) {
//        for (Pattern pattern :
//                patterns) {
//            loggerSender.afterReturningDeArchive(pattern.getId(), object);
//        }
//    }
//
//    @AfterReturning(pointcut = "callDeArchivePatternsAfterReturning(id)", returning = "patterns", argNames = "patterns,id")
//    public void afterReturningDeArchivePatterns(List<Pattern> patterns, Long id) {
//        for (Pattern pattern :
//                patterns) {
//            loggerSender.afterReturningDeArchive(pattern.getId(), object);
//        }
//    }
//
//    @AfterReturning(pointcut = "callUpdatePatternAfterReturning(pattern)", returning = "patternAfter", argNames = "pattern,patternAfter")
//    public void afterReturningUpdatePattern(Pattern pattern, Pattern patternAfter) {
//        loggerSender.afterReturningUpdate(patternAfter.getId(), object);
//    }


}
