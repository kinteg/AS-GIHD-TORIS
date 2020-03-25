package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

public interface PatternRepo extends JpaRepository<Pattern, Long> {
    Page<Pattern> findAllBySourceId(Long sourceId, Pageable pageable);

    Page<Pattern> findAll(Pageable pageable);
    Page<Pattern> findAllByIsArchive(Boolean isArchive, Pageable pageable);
    Page<Pattern> findAllBySourceIdAndIsArchive(long sourceId, boolean isArchive, Pageable pageable);

    Pattern findById(long id);
    boolean existsById(long id);

}
