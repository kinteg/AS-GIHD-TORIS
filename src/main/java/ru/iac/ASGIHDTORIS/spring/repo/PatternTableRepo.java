package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.List;

public interface PatternTableRepo extends JpaRepository<PatternTable, Long> {
    Page<PatternTable> findAllByPatternId(long id, Pageable pageable);

    Page<PatternTable> findAllByPatternIdAndIsArchive(long id, boolean isArchive, Pageable pageable);

    Page<PatternTable> findAllBySourceIdAndIsArchive(long id, boolean isArchive, Pageable pageable);

    List<PatternTable> findAllByPatternIdAndIsArchive(long id, boolean isArchive);

    Page<PatternTable> findAllByIsArchive(boolean isArchive, Pageable pageable);

    Page<PatternTable> findAllBySourceId(long id, Pageable pageable);

    List<PatternTable> findAllBySourceId(long id);

    PatternTable findById(long id);

    boolean existsBySourceId(long id);

    boolean existsByPatternId(long id);

    boolean existsByNameTable(String name);
}
