package ru.iac.ASGIHDTORIS.common.validator.checkDataValidator.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.validator.checkDataValidator.DataCheckValidator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;

import java.io.File;

@Component
public class DataCheckValidatorImpl implements DataCheckValidator {

    private final PatternTableRepo patternTableRepo;
    private final PatternRepo patternRepo;

    public DataCheckValidatorImpl(PatternTableRepo patternTableRepo, PatternRepo patternRepo) {
        this.patternTableRepo = patternTableRepo;
        this.patternRepo = patternRepo;
    }

    @Override
    public boolean checkByPatternId(File file, Long id) {
        return isValidId(id) && isValidIdPattern(id) && fileIsNotNull(file);
    }

    @Override
    public boolean checkByPatternTableId(File file, Long id) {
        return isValidId(id) && isValidIdPatternTable(id) && fileIsNotNull(file);
    }

    private boolean isValidId(Long id) {
        return idIsNotNull(id) && isNormalId(id);
    }

    private boolean isValidIdPatternTable(long id) {

        if (existPatternTableById(id)) {
            PatternTable patternTable = patternTableRepo.findById(id);

            return existPatternTableByIdAndIsNotArchive(patternTable) &&
                    existPatternTableByIdAndIsActive(patternTable);
        }

        return false;
    }

    private boolean isValidIdPattern(long id) {

        if (existPattenById(id)) {
            Pattern pattern = patternRepo.findById(id);

            return existPattenByIdAndNotArchive(pattern);
        }

        return false;
    }

    private boolean fileIsNotNull(File file) {
        return file != null;
    }

    private boolean idIsNotNull(Long id) {
        return id != null;
    }

    private boolean isNormalId(Long id) {
        return id > 0;
    }

    private boolean existPatternTableById(long id) {
        return patternTableRepo.existsById(id);
    }

    private boolean existPatternTableByIdAndIsNotArchive(PatternTable patternTable) {
        return !patternTable.getIsArchive();
    }

    private boolean existPatternTableByIdAndIsActive(PatternTable patternTable) {
        return patternTable.getIsActive();
    }

    private boolean existPattenById(long id) {
        return patternRepo.existsById(id);
    }

    private boolean existPattenByIdAndNotArchive(Pattern pattern) {
        return !pattern.getIsArchive();
    }

}
