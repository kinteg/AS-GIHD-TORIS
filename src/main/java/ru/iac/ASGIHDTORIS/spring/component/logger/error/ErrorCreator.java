package ru.iac.ASGIHDTORIS.spring.component.logger.error;

import ru.iac.ASGIHDTORIS.spring.domain.Errors;

public interface ErrorCreator {

    Errors errorCreator(Long id);

}
