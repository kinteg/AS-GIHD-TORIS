package ru.iac.ASGIHDTORIS.spring.service.source.logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Service
public class SourceLoggerServiceImpl implements SourceLoggerService {

    private final LoggerSender<Source> sourceLoggerSender;
    private final BeforeAfter<Source> sourceBeforeAfter;

    public SourceLoggerServiceImpl(
            @Qualifier("sourceLoggerSender") LoggerSender<Source> sourceLoggerSender,
            @Qualifier("sourceBeforeAfter") BeforeAfter<Source> sourceBeforeAfter) {

        this.sourceLoggerSender = sourceLoggerSender;
        this.sourceBeforeAfter = sourceBeforeAfter;
    }

    @Override
    public void createLogSourceCreate(Source sourceAfter, User user) {
        long loggerId = sourceLoggerSender.afterCreate(sourceAfter, user);

        if (sourceAfter != null && sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterCreate(sourceAfter, loggerId);
        }

    }

    @Override
    public void createLogSourceArchive(Source sourceBefore, Source sourceAfter, User user) {

        long loggerId = sourceLoggerSender.afterArchive(sourceAfter, user);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterArchive(sourceBefore, sourceAfter, loggerId);
        }

    }

    @Override
    public void createLogSourceDeArchive(Source sourceBefore, Source sourceAfter, User user) {

        long loggerId = sourceLoggerSender.afterDeArchive(sourceAfter, user);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterDeArchive(sourceBefore, sourceAfter, loggerId);
        }

    }

    @Override
    public void createLogSourceUpdate(Source sourceBefore, Source sourceAfter, User user) {

        long loggerId = sourceLoggerSender.afterUpdate(sourceAfter, user);

        if (sourceBefore.getId() > 0) {
            sourceBeforeAfter.afterUpdate(sourceBefore, sourceAfter, loggerId);
        }

    }
}
