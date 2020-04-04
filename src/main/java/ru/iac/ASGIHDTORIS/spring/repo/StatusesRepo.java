package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Errors;
import ru.iac.ASGIHDTORIS.spring.domain.Objects;
import ru.iac.ASGIHDTORIS.spring.domain.Statuses;

public interface StatusesRepo extends JpaRepository<Statuses, Long> {

    Statuses findById(long id);

}
