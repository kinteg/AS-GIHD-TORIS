package ru.iac.ASGIHDTORIS.service.pattern.valid;

import org.springframework.stereotype.Service;

@Service
public interface PatternValidatorService {

    boolean isValid(String name);

}
