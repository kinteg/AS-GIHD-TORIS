package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

import java.util.List;

@Service
public interface PatternService {

    Pattern createPattern(Pattern pattern);

    Pattern archivePattern(Long id);

    Pattern deArchivePattern(Long id);

    List<Pattern> archivePatterns(Long sourceId);

    List<Pattern> deArchivePatterns(Long sourceId);

    Pattern updatePattern(Pattern pattern);

}
