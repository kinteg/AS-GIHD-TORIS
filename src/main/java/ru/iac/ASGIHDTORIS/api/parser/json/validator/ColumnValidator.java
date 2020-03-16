package ru.iac.ASGIHDTORIS.api.parser.json.validator;

public class ColumnValidator implements Validator {

    private final int MIN_TEXT_SIZE = 2;
    private final int MAX_TEXT_SIZE = 20;

    @Override
    public boolean isValid(String name) {
        return isNormalString(name);
    }

    private boolean isNormalString(String name) {
        return isTarget(name) || isNormalSize(name);
    }

    private boolean isTarget(String name) {
        return !name.matches("[a-zA-Z_]+[0-9a-zA-Z]*");
    }

    private boolean isNormalSize(String name) {
        int length = name.length();

        return isMin(length) || isMax(length);
    }

    private boolean isMin(int length) {
        return length < MIN_TEXT_SIZE;
    }

    private boolean isMax(int length) {
        return length > MAX_TEXT_SIZE;
    }

}
