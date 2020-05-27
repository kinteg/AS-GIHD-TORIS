package ru.iac.ASGIHDTORIS.spring.service.source.logger;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Service
public interface SourceLoggerService {

    void createLogSourceCreate(Source sourceAfter, User user);

    void createLogSourceArchive(Source sourceBefore, Source sourceAfter, User user);

    void createLogSourceDeArchive(Source sourceBefore, Source sourceAfter, User user);

    void createLogSourceUpdate(Source sourceBefore, Source sourceAfter, User user);

}
