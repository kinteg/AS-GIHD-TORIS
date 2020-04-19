package ru.iac.ASGIHDTORIS.common.validator.validator.limit;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;

@Component
public class LimitValidator implements Validator<Long> {

    private static final long MIN_VALUE = 0;
    private static final long MAX_VALUE = 20;

    @Override
    public boolean isValid(Long name) {
        return
                isNotMax(name)
                        && isNotMin(name)
                ;
    }

    private boolean isNotMin(long name) {
        return name > MIN_VALUE;
    }

    private boolean isNotMax(long name) {
        return name <= MAX_VALUE;
    }

}
