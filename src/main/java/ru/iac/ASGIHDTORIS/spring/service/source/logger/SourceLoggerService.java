package ru.iac.ASGIHDTORIS.spring.service.source.logger;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

@Service
public interface SourceLoggerService {

    void createLogSourceCreate(Source sourceAfter);

    void createLogSourceArchive(Source sourceBefore, Source sourceAfter);

    void createLogSourceDeArchive(Source sourceBefore, Source sourceAfter);

    void createLogSourceUpdate(Source sourceBefore, Source sourceAfter);

}
