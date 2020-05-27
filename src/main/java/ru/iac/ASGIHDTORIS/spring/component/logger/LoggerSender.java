package ru.iac.ASGIHDTORIS.spring.component.logger;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.List;

@Component
public interface LoggerSender<T> {

    Long afterCreate(T object, User user);

    Long afterArchive(T object, User user);

    List<Long> afterArchive(List<T> object, User user);

    Long afterDeArchive(T object, User user);

    List<Long> afterDeArchive(List<T> object, User user);

    Long afterUpdate(T object, User user);

}
