package ru.iac.ASGIHDTORIS.spring.component.logger.error;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Errors;
import ru.iac.ASGIHDTORIS.spring.repo.ErrorsRepo;

@Component
public class ErrorCreatorImpl implements ErrorCreator{

    private final ErrorsRepo errorsRepo;

    public ErrorCreatorImpl(ErrorsRepo errorsRepo) {
        this.errorsRepo = errorsRepo;
    }

    public Errors errorCreator(Long id) {
        return createError(id);
    }

    private Errors createError(Long id) {
        Errors error;

        if (id == null) {
            error = errorsRepo.findById(5);

        } else if (id == -1) {
            error = errorsRepo.findById(2);

        } else if (id == -2) {
            error = errorsRepo.findById(3);

        } else if (id == -3) {
            error = errorsRepo.findById(4);

        } else {
            error = errorsRepo.findById(1);
        }

        return error;
    }
}
