package ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.impl;

import ru.iac.ASGIHDTORIS.lib.lib.parser.file.fixer.TypeGenerator;
import ru.iac.ASGIHDTORIS.lib.lib.validator.TypeValidator;
import ru.iac.ASGIHDTORIS.lib.lib.validator.impl.TypeValidatorImpl;

import java.util.List;

public class TypeGeneratorImpl implements TypeGenerator {
    
    private final TypeValidator typeValidator;

    public TypeGeneratorImpl() {
        typeValidator = new TypeValidatorImpl();
    }

    @Override
    public String getType(List<String> values) {
        if (typeValidator.isInt(values)) {
            return "integer";
        } else if (typeValidator.isLong(values)) {
            return "bigint";
        } else if (typeValidator.isDouble(values)) {
            return "double precision";
        } else if (typeValidator.isTime(values)) {
            return "time";
        } else if (typeValidator.isDate(values)) {
            return "date";
        } else if (typeValidator.isDateTime(values)) {
            return "timestamp";
        } else {
            return "text";
        }

    }


}
