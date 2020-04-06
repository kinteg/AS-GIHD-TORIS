package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.SourceLogger;

public interface SourceLoggerRepo extends JpaRepository<SourceLogger, Long> {

    SourceLogger findById(long id);

    Page<SourceLogger> findAll(Pageable pageable);

    Page<SourceLogger> findAllBySourceId(Pageable pageable, long sourceId);

}
