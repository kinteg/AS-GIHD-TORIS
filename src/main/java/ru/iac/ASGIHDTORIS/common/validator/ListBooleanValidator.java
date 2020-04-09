package ru.iac.ASGIHDTORIS.common.validator;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListBooleanValidator implements Validator<List<Boolean>> {

    @Override
    public boolean isValid(List<Boolean> name) {
        return
                isNotNull(name)
                        && isNotNullItems(name);
    }

    private boolean isNotNull(List<Boolean> name) {
        return name != null;
    }

    private boolean isNotNullItems(List<Boolean> name) {
        return !name.contains(null);
    }

}
