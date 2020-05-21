package ru.iac.ASGIHDTORIS.lib.lib.validator.impl;

import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFile;
import ru.iac.ASGIHDTORIS.lib.lib.common.target.file.TargetFileImpl;
import ru.iac.ASGIHDTORIS.lib.lib.validator.ArchiveValidator;

public class ArchiveValidatorImpl implements ArchiveValidator {

    private final TargetFile targetFile;

    public ArchiveValidatorImpl() {
        targetFile = new TargetFileImpl();
    }

    @Override
    public boolean isValidEntry(String filename, String name) {
        return isTargetFile(filename) &&
                isTargetFile(filename, name);
    }

    @Override
    public boolean isTargetFile(String filename) {
        return targetFile.isTargetFile(filename.toLowerCase());
    }

    @Override
    public boolean isTargetFile(String filename, String name) {
        return filename.toLowerCase().equals(name.toLowerCase());
    }
}
