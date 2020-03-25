package ru.iac.ASGIHDTORIS.common.validator.domain;

import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

public class PatternValidator implements Validator<Pattern> {

    private final SourceRepo sourceRepo;

    public PatternValidator(SourceRepo sourceRepo) {
        this.sourceRepo = sourceRepo;
    }

    @Override
    public boolean isValid(Pattern name) {
        return isValidFileCount(name.getFileCount())
                && isValidName(name.getName())
                && isValidDescription(name.getDescription())
                && isValidDirection(name.getDirection())
                && isValidManagement(name.getManagement())
                && isValidArchive(name.getIsArchive())
                && isValidDateCreation(name.getDescription())
                && isValidSourceId(name.getSourceId())
                ;
    }

    private boolean isValidFileCount(Integer fileCount) {
        return fileCount != null && fileCount > 0;
    }

    private boolean isValidName(String name) {
        return name != null && name.equals("");
    }

    private boolean isValidDescription(String description) {
        return description != null && description.equals("");
    }

    private boolean isValidDirection(String direction) {
        return direction != null && direction.equals("");
    }

    private boolean isValidManagement(String management) {
        return management != null && management.equals("");
    }

    private boolean isValidArchive(Boolean isArchive) {
        return isArchive != null;
    }

    private boolean isValidDateCreation(String dateCreation) {
        return dateCreation != null;
    }

    private boolean isValidSourceId(Long sourceId) {
        return sourceId != null && sourceRepo.existsById(sourceId);
    }

}
