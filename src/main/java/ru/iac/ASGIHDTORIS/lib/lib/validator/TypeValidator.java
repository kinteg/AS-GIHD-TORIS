package ru.iac.ASGIHDTORIS.lib.lib.validator;

import java.util.List;

public interface TypeValidator {

    boolean isInt(List<String> values);

    boolean isLong(List<String> values);

    boolean isDouble(List<String> values);

    boolean isDate(List<String> values);

    boolean isDateTime(List<String> values);

    boolean isTime(List<String> values);

}
