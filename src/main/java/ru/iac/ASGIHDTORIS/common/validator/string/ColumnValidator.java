package ru.iac.ASGIHDTORIS.common.validator.string;

import ru.iac.ASGIHDTORIS.common.validator.Validator;

public class ColumnValidator implements Validator<String> {
    private static final int MIN_TEXT_SIZE = 2;
    private static final int MAX_TEXT_SIZE = 20;

    @Override
    public boolean isValid(String name) {
        return isNormalString(name);
    }

    private boolean isNormalString(String name) {
        return isTarget(name) || isNormalSize(name);
    }

    private boolean isTarget(String name) {
        return name.matches("^[a-zA-Zа-яА-Я]+[_0-9a-zA-Zа-яА-Я ]*");
    }

    private boolean isNormalSize(String name) {
        int length = name.length();

        return !isMin(length) && !isMax(length);
    }

    private boolean isMin(int length) {
        return length < MIN_TEXT_SIZE;
    }

    private boolean isMax(int length) {
        return length > MAX_TEXT_SIZE;
    }

}
