package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableLogger;

public interface PatternTableLoggerRepo extends JpaRepository<PatternTableLogger, Long> {

    PatternTableLogger findById(long id);

    Page<PatternTableLogger> findAll(Pageable pageable);

    Page<PatternTableLogger> findAllByPatternTableId(Pageable pageable, long patternTableId);

}
