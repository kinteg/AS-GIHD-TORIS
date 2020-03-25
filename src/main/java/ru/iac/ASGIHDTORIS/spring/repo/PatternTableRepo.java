package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.List;

public interface PatternTableRepo extends JpaRepository<PatternTable, Long> {
    Page<PatternTable> findAllByPatternId(long id, Pageable pageable);
    List<PatternTable> findAllByPatternId(long id);

    PatternTable findById(long id);
}
