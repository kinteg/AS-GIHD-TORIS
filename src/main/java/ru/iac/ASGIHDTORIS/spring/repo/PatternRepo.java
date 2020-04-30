package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

import java.time.LocalDateTime;
import java.util.List;

public interface PatternRepo extends JpaRepository<Pattern, Long> {


    Page<Pattern> findAllBySourceId(Long sourceId, Pageable pageable);

    List<Pattern> findAllBySourceId(Long sourceId);

    @Override
    Page<Pattern> findAll(Pageable pageable);

    Page<Pattern> findAllByIsArchive(Boolean isArchive, Pageable pageable);

    Page<Pattern> findAllBySourceIdAndIsArchive(long sourceId, boolean isArchive, Pageable pageable);

    Pattern findById(long id);

    boolean existsById(long id);

    boolean existsBySourceId(long id);

    @Query(value = "SELECT archive FROM pattern WHERE " +
            "id = :id", nativeQuery = true)
    boolean isArchive(@Param("id") long id);

}
