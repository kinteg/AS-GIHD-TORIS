package ru.iac.ASGIHDTORIS.spring.service.pattern.logger;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.List;

@Service
public interface PatternLoggerService {

    void createLogCreate(Pattern patternAfter, User user);

    void createLogPatternsArchive(List<Pattern> patternBefore, List<Pattern> patternAfter, User user);

    void createLogPatternArchive(Pattern patternBefore, Pattern patternAfter, User user);

    void createLogPatternsDeArchive(List<Pattern> patternBefore, List<Pattern> patternAfter, User user);

    void createLogPatternDeArchive(Pattern patternBefore, Pattern patternAfter, User user);

    void createLogPatternUpdate(Pattern patternBefore, Pattern patternAfter, User user);

    void createLogIncrement(Pattern patternBefore, Pattern patternAfter, User user);

    void createLogDecrement(Pattern patternBefore, Pattern patternAfter, User user);

}
