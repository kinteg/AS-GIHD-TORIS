package ru.iac.ASGIHDTORIS.spring.controller.aspect.logger;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Objects;

@Component
public interface LoggerSender {

    void afterReturningCreate(Long id, Objects object);

    void afterReturningArchive(Long id, Objects object);

    void afterReturningDeArchive(Long id, Objects object);

    void afterReturningUpdate(Long id, Objects object);

}
