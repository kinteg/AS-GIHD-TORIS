package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternDateModel;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceDateModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

public interface PatternService {

    Page<Pattern> findAllPatternByQuery(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel, String key, String sort);

}
