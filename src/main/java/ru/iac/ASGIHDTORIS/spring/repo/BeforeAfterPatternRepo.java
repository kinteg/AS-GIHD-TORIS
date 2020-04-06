package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPattern;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterSource;

public interface BeforeAfterPatternRepo extends JpaRepository<BeforeAfterPattern, Long> {

    BeforeAfterPattern findById(long id);

    Page<BeforeAfterPattern> findAll(Pageable pageable);
    Page<BeforeAfterPattern> findAllByPatternLoggerId(Pageable pageable, long patternLoggerId);

}
