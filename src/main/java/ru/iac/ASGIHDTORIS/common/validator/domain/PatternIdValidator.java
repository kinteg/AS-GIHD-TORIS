package ru.iac.ASGIHDTORIS.common.validator.domain;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

@Component
public class PatternIdValidator implements Validator<Long> {

    private final PatternRepo patternRepo;

    public PatternIdValidator(PatternRepo patternRepo) {
        this.patternRepo = patternRepo;
    }

    @Override
    public boolean isValid(Long name) {
        return
                isNotNull(name)
                && isValidId(name)
                && exist(name);
    }

    private boolean isNotNull(Long name) {
        return name != null;
    }

    private boolean isValidId(Long name) {
        return name >= 0;
    }

    private boolean exist(Long name) {
        return patternRepo.existsById(name);
    }
}
