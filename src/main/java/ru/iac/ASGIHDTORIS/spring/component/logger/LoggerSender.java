package ru.iac.ASGIHDTORIS.spring.component.logger;

import org.springframework.stereotype.Component;

@Component
public interface LoggerSender<T> {

    Long afterCreate(T object);

    Long afterArchive(T object);

    Long afterDeArchive(T object);

    Long afterUpdate(T object);

}
