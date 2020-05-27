package ru.iac.ASGIHDTORIS.spring.service.pattern.logger;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.List;

@Service
public class PatternLoggerServiceImpl implements PatternLoggerService {

    private final LoggerSender<Pattern> patternLoggerSender;
    private final BeforeAfter<Pattern> patternBeforeAfter;

    public PatternLoggerServiceImpl(
            @Qualifier("patternLoggerSender") LoggerSender<Pattern> patternLoggerSender,
            @Qualifier("patternBeforeAfter") BeforeAfter<Pattern> patternBeforeAfter) {

        this.patternLoggerSender = patternLoggerSender;
        this.patternBeforeAfter = patternBeforeAfter;
    }

    @Override
    public void createLogCreate(Pattern patternAfter, User user) {
        long loggerId = patternLoggerSender.afterCreate(patternAfter, user);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterCreate(patternAfter, loggerId);
        }

    }

    @Override
    public void createLogPatternsArchive(List<Pattern> patternBefore, List<Pattern> patternAfter, User user) {
        for (int i = 0; i < patternAfter.size() && i < patternBefore.size(); i++) {
            createLogPatternArchive(patternBefore.get(i), patternAfter.get(i), user);
        }
    }

    @Override
    public void createLogPatternArchive(Pattern patternBefore, Pattern patternAfter, User user) {
        long loggerId = patternLoggerSender.afterArchive(patternAfter, user);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }
    }

    @Override
    public void createLogPatternsDeArchive(List<Pattern> patternBefore, List<Pattern> patternAfter, User user) {
        for (int i = 0; i < patternAfter.size() && i < patternBefore.size(); i++) {
            createLogPatternDeArchive(patternBefore.get(i), patternAfter.get(i), user);
        }
    }

    @Override
    public void createLogPatternDeArchive(Pattern patternBefore, Pattern patternAfter, User user) {
        long loggerId = patternLoggerSender.afterArchive(patternAfter, user);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }
    }

    @Override
    public void createLogPatternUpdate(Pattern patternBefore, Pattern patternAfter, User user) {

        long loggerId = patternLoggerSender.afterUpdate(patternAfter, user);

        if (patternBefore.getId() > 0) {
            patternBeforeAfter.afterUpdate(patternBefore, patternAfter, loggerId);
        }

    }

    @Override
    public void createLogIncrement(Pattern patternBefore, Pattern patternAfter, User user) {
        long loggerId = patternLoggerSender.afterUpdate(patternAfter, user);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterUpdate(patternBefore, patternAfter, loggerId);
        }
    }

    @Override
    public void createLogDecrement(Pattern patternBefore, Pattern patternAfter, User user) {
        long loggerId = patternLoggerSender.afterUpdate(patternAfter, user);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterUpdate(patternBefore, patternAfter, loggerId);
        }
    }
}
