package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Objects;

public interface ObjectsRepo extends JpaRepository<Objects, Long> {

    Objects findById(long id);

}
