package ru.iac.ASGIHDTORIS.common.validator.archiveValidator.impl;

import ru.iac.ASGIHDTORIS.common.TargetFiles;
import ru.iac.ASGIHDTORIS.common.validator.archiveValidator.ArchiveValidator;

public class ArchiveValidatorImpl implements ArchiveValidator {
    @Override
    public boolean isValidEntry(String filename, String name) {
        return isTargetFile(filename) &&
                isTargetFile(filename, name);
    }

    @Override
    public boolean isTargetFile(String filename) {
        return TargetFiles.isTargetFile(filename.toLowerCase());
    }

    @Override
    public boolean isTargetFile(String filename, String name) {
        return filename.toLowerCase().equals(name.toLowerCase());
    }
}
