package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPatternTable;

public interface BeforeAfterPatternTableRepo extends JpaRepository<BeforeAfterPatternTable, Long> {

    BeforeAfterPatternTable findById(long id);

    Page<BeforeAfterPatternTable> findAll(Pageable pageable);

    Page<BeforeAfterPatternTable> findAllByPatternTableLoggerId(Pageable pageable, long patternTableLoggerId);

}
