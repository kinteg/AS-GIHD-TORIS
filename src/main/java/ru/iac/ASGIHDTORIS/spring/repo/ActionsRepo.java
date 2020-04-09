package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Actions;

public interface ActionsRepo extends JpaRepository<Actions, Long> {

    Actions findById(long id);

}
