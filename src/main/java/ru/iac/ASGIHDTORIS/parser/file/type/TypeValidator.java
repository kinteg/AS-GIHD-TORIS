package ru.iac.ASGIHDTORIS.parser.file.type;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.TimeValidator;

import java.util.List;

@Slf4j
public class TypeValidator {

    public boolean isInt(List<String> values) {
        for (String value :
                values) {
            int number = NumberUtils.toInt(value);

            if (number == 0 && !value.equals(String.valueOf(number))) {
                return false;
            }

        }

        return true;
    }

    public boolean isLong(List<String> values) {
        for (String value :
                values) {
            long number = NumberUtils.toLong(value);

            if (number == 0 && !value.equals(String.valueOf(number))) {
                return false;
            }

        }

        return true;
    }

    public boolean isFloat(List<String> values) {
        for (String value :
                values) {
            float number = NumberUtils.toFloat(value);

            if (number == 0 && !value.equals(String.valueOf(number))) {
                return false;
            }

        }

        return true;
    }

    public boolean isDouble(List<String> values) {
        for (String value :
                values) {
            double number = NumberUtils.toDouble(value);

            if (value == null || number == 0 && !value.equals(String.valueOf(number))) {
                return false;
            }

        }

        return true;
    }

    public boolean isDate(List<String> values) {
        DateValidator dateValidator = DateValidator.getInstance();
        boolean isValid = false;

        for (String value :
                values) {

            isValid = dateValidator.isValid(value, "dd.MM.yyyy")
                    || dateValidator.isValid(value, "dd/MM/yyyy")
                    || dateValidator.isValid(value, "dd-MM-yyyy");

            if (!isValid) {
                break;
            }
        }

        return isValid;
    }

    public boolean isDateTime(List<String> values) {
        DateValidator dateValidator = DateValidator.getInstance();
        boolean isValid = false;

        for (String value :
                values) {

            isValid = dateValidator.isValid(value)
                    || dateValidator.isValid(value, "dd.mm.yyyy HH:mm:ss.SSS")
                    || dateValidator.isValid(value, "dd.mm.yyyy HH:mm:ss");

            if (!isValid) {
                break;
            }
        }

        return isValid;
    }

    public boolean isTime(List<String> values) {
        TimeValidator timeValidator = TimeValidator.getInstance();
        boolean isValid = false;

        for (String value :
                values) {

            isValid = timeValidator.isValid(value, "HH:mm:ss")
                    || timeValidator.isValid(value, "HH:mm:ss.SSS");

            if (!isValid) {
                break;
            }
        }

        return isValid;
    }

}
