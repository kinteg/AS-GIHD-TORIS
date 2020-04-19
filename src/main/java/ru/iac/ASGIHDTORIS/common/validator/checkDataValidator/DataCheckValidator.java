package ru.iac.ASGIHDTORIS.common.validator.checkDataValidator;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface DataCheckValidator {

    boolean checkByPatternId(File file, Long id);

    boolean checkByPatternTableId(File file, Long id);

}
