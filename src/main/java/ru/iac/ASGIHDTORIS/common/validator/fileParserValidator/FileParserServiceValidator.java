package ru.iac.ASGIHDTORIS.common.validator.fileParserValidator;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public interface FileParserServiceValidator {

    boolean valid(File file, long limit);

    boolean valid(File file, long limit,
                  long patternId);

    boolean valid(File file, long limit,
                  String patternTableName,
                  String patternNameFile);


}
