package ru.iac.ASGIHDTORIS.spring.service.pattern.logger;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

import java.util.List;

@Service
public interface PatternLoggerService {

    void createLogCreate(Pattern patternAfter);

    void createLogPatternsArchive(List<Pattern> patternBefore, List<Pattern> patternAfter);

    void createLogPatternArchive(Pattern patternBefore, Pattern patternAfter);

    void createLogPatternsDeArchive(List<Pattern> patternBefore, List<Pattern> patternAfter);

    void createLogPatternDeArchive(Pattern patternBefore, Pattern patternAfter);

    void createLogPatternUpdate(Pattern patternBefore, Pattern patternAfter);

    void createLogIncrement(Pattern patternBefore, Pattern patternAfter);

    void createLogDecrement(Pattern patternBefore, Pattern patternAfter);

}
