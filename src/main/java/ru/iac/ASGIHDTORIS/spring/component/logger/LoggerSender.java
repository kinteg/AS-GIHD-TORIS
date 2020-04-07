package ru.iac.ASGIHDTORIS.spring.component.logger;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface LoggerSender<T> {

    Long afterCreate(T object);

    Long afterArchive(T object);

    List<Long> afterArchive(List<T> object);

    Long afterDeArchive(T object);

    List<Long> afterDeArchive(List<T> object);

    Long afterUpdate(T object);

}
