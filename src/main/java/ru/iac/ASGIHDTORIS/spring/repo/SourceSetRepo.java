package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.SourceSet;

public interface SourceSetRepo extends JpaRepository<SourceSet, Long> {

    boolean existsBySourceIdAndUserId(Long sourceId, Long userId);

}
