package ru.iac.ASGIHDTORIS.spring.controller.aspect.logger;

import org.springframework.stereotype.Component;

@Component
public interface LoggerSender<T> {

    void afterReturningCreate(Long id, T object);

    void afterReturningArchive(Long id, T object);

    void afterReturningDeArchive(Long id, T object);

    void afterReturningUpdate(Long id, T object);

}
