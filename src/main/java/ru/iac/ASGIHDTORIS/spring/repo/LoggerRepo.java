package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.SourceLogger;

public interface LoggerRepo extends JpaRepository<SourceLogger, Long> {
}
