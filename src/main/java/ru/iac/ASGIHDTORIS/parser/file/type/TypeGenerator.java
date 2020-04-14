package ru.iac.ASGIHDTORIS.parser.file.type;

import java.util.List;

public final class TypeGenerator {

    private TypeGenerator() {
    }

    public static String type(List<String> values) {

        TypeValidator typeValidator = new TypeValidator();

        return
                typeValidator.isInt(values) ? "integer" :
                        typeValidator.isLong(values) ? "bigint" :
                                typeValidator.isDouble(values) ? "double precision" :
                                        typeValidator.isTime(values) ? "time" :
                                                typeValidator.isDate(values) ? "date" :
                                                        typeValidator.isDateTime(values) ? "timestamp" : "text";

    }


}
