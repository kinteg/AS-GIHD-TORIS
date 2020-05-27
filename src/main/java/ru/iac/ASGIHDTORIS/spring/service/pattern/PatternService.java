package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.List;

@Service
public interface PatternService {

    Pattern createPattern(Pattern pattern, User user);

    Pattern archivePattern(Long id, User user);

    Pattern deArchivePattern(Long id, User user);

    List<Pattern> archivePatterns(Long sourceId, User user);

    List<Pattern> deArchivePatterns(Long sourceId, User user);

    Pattern updatePattern(Pattern pattern, User user);

    void incrementFiles(List<Long> id, User user);

    void incrementFiles(Long id, int count, User user);

    void decrementFiles(List<Long> id, User user);

    void decrementFiles(Long id, int count, User user);

}
