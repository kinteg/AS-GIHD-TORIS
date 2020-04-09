package ru.iac.ASGIHDTORIS.common.validator;

import org.springframework.stereotype.Component;

@Component
public interface Validator<T> {

    boolean isValid(T name);

}
