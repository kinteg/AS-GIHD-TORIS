package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Actions;
import ru.iac.ASGIHDTORIS.spring.domain.Errors;

public interface ErrorsRepo extends JpaRepository<Errors, Long> {

    Errors findById(long id);

}
