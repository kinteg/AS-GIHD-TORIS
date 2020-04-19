package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.List;

public interface PatternTableRepo extends JpaRepository<PatternTable, Long> {
    Page<PatternTable> findAllByPatternIdAndIsActive(long id, Pageable pageable, boolean isActive);

    List<PatternTable> findAllByPatternIdAndIsActive(long id, boolean isActive);

    Page<PatternTable> findAllByPatternIdAndIsArchiveAndIsActive(long id, boolean isArchive, Pageable pageable, boolean isActive);

    Page<PatternTable> findAllBySourceIdAndIsArchiveAndIsActive(long id, boolean isArchive, Pageable pageable, boolean isActive);

    List<PatternTable> findAllByPatternIdAndIsArchiveAndIsActive(long id, boolean isArchive, boolean isActive);

    Page<PatternTable> findAllByIsArchiveAndIsActive(boolean isArchive, Pageable pageable, boolean isActive);

    Page<PatternTable> findAllBySourceIdAndIsActive(long id, Pageable pageable, boolean isActive);

    List<PatternTable> findAllBySourceIdAndIsActive(long id, boolean isActive);

    Page<PatternTable> findAllByIsActive(Pageable pageable, boolean isActive);

    PatternTable findById(long id);

    PatternTable findByNameTable(String nameTable);

    Page<PatternTable> findAllByOldNameAndIsActive(String oldName, boolean active, Pageable pageable);

    boolean existsBySourceId(long id);

    boolean existsByPatternId(long id);

    boolean existsByNameTable(String name);

    boolean existsByNameFile(String name);
}
