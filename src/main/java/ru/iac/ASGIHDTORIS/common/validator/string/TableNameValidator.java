package ru.iac.ASGIHDTORIS.common.validator.string;

import ru.iac.ASGIHDTORIS.common.validator.Validator;

public class TableNameValidator implements Validator<String> {

    @Override
    public boolean isValid(String name) {

        return !(isNull(name)
        || isEmpty(name))
        && isCorrectLength(name)
        && isCorrectName(name);
    }

    private boolean isNull(String name) {
        return name == null;
    }

    private boolean isEmpty(String name) {
        return name.equals("");
    }

    private boolean isCorrectLength(String name) {
        return name.length() > 1 && name.length() <= 20;
    }

    private boolean isCorrectName(String name) {
        return name.matches("^[A-Za-zА-Яа-я][A-Za-z0-9А-Яа-я_]+");
    }

}
