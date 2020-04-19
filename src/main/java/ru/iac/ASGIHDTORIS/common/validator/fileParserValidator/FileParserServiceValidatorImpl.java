package ru.iac.ASGIHDTORIS.common.validator.fileParserValidator;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;

import java.io.File;

@Component
public class FileParserServiceValidatorImpl implements FileParserServiceValidator {

    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final Validator<Long> limitValidator;

    public FileParserServiceValidatorImpl(
            PatternRepo patternRepo,
            PatternTableRepo patternTableRepo,
            @Qualifier(value = "limitValidator") Validator<Long> limitValidator) {

        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.limitValidator = limitValidator;
    }


    @Override
    public boolean valid(File file, long limit) {
        return fileIsNotNull(file) &&
                betweenLimits(limit);
    }

    @Override
    public boolean valid(File file, long limit, long patternId) {
        return fileIsNotNull(file) &&
                betweenLimits(limit) &&
                patternIdIsExist(patternId);
    }

    @Override
    public boolean valid(File file, long limit, String patternTableName, String patternNameFile) {
        return fileIsNotNull(file) &&
                betweenLimits(limit) &&
                tableNameIsExist(patternTableName) &&
                fileNameIsExist(patternNameFile);
    }

    private boolean fileIsNotNull(File file) {
        return file != null;
    }

    private boolean betweenLimits(long limit) {
        return limitValidator.isValid(limit);
    }

    private boolean patternIdIsExist(long patternId) {
        return patternRepo.existsById(patternId);
    }

    private boolean tableNameIsExist(String patternTableName) {
        return patternTableRepo.existsByNameTable(patternTableName);
    }

    private boolean fileNameIsExist(String patternNameFile) {
        return patternTableRepo.existsByNameFile(patternNameFile);
    }

}
