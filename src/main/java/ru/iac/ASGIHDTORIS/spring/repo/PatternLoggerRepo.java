package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.PatternLogger;
import ru.iac.ASGIHDTORIS.spring.domain.SourceLogger;

public interface PatternLoggerRepo extends JpaRepository<PatternLogger, Long> {

    PatternLogger findById(long id);

    Page<PatternLogger> findAll(Pageable pageable);

    Page<PatternLogger> findAllByPatternId(Pageable pageable, long patternId);

}
