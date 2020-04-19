package ru.iac.ASGIHDTORIS.common.validator.validator.string;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;

import java.util.List;

@Component
public class ListStringValidator implements Validator<List<String>> {

    @Override
    public boolean isValid(List<String> name) {
        return
                isNotNull(name)
                        && isNotEmpty(name)
                        && isNotNullItems(name);
    }

    private boolean isNotNull(List<String> name) {
        return name != null;
    }

    private boolean isNotEmpty(List<String> name) {
        return !name.isEmpty();
    }

    private boolean isNotNullItems(List<String> name) {
        return !name.contains(null);
    }

}
