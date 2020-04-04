package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.Logger;
import ru.iac.ASGIHDTORIS.spring.repo.LoggerRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

@Aspect
@Component
@Slf4j
public class SourceControllerAspect {

    private final String OBJECT = "Source";

    private final LoggerRepo loggerRepo;
    private final SourceRepo sourceRepo;

    public SourceControllerAspect(LoggerRepo loggerRepo, SourceRepo sourceRepo) {
        this.loggerRepo = loggerRepo;
        this.sourceRepo = sourceRepo;
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.createSource(..)) && args(source)")
    public void callCreateSource(Source source) {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.createSource())")
    public void callCreateSourceAfterReturning() {
    }

    @Before(value = "callCreateSource(source)", argNames = "jp,source")
    public void beforeCallCreateSource(JoinPoint jp, Source source) {
        log.info(source.toString());

        Logger logger = Logger
                .builder()
                .action("Попытка добавления источника")
                .status("Ожидается добавление")
                .error("-")
                .object("Source")
                .build();

        loggerRepo.save(logger);
    }

    @AfterReturning(pointcut = "callCreateSourceAfterReturning()", returning = "source")
    public void afterReturningCreateSource(Source source) {
        String error;
        String status;

        if (source.getId() == null) {
            error = "Ошибка валидации";
            status = "Источник не добавлен";
        } else if (source.getId() != null) {
            error = "-";
            status = "Источник добавлен";
        } else {
            error = "Такое имя уже существует";
            status = "Источник не добавлен";
        }

        Logger logger = Logger
                .builder()
                .error(error)
                .status(status)
                .object(OBJECT)
                .action("Добавление источника")
                .build();
    }

}
