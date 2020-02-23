package ru.iac.ASGIHDTORIS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.domain.PatternTable;

import java.util.List;

public interface PatternTableRepo extends JpaRepository<PatternTable,Long> {
    List<PatternTable> findByPatternId(long id);
}
