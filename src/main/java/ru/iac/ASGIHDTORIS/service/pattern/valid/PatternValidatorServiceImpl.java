package ru.iac.ASGIHDTORIS.service.pattern.valid;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;

@Service
public class PatternValidatorServiceImpl implements PatternValidatorService {

    private final PatternRepo patternRepo;

    public PatternValidatorServiceImpl(PatternRepo patternRepo) {
        this.patternRepo = patternRepo;
    }

    @Override
    public boolean isValid(String name) {
        boolean isNull = isNull(name);
        boolean isEmpty = isEmpty(name);
        boolean isContains = isContains(name);

        return isNull && isEmpty && isContains;
    }

    private boolean isNull(String name) {
        return name != null;
    }

    private boolean isEmpty(String name) {
        return !name.isEmpty();
    }

    private boolean isContains(String name) {
        return patternRepo.findByName(name) == null;
    }

}
