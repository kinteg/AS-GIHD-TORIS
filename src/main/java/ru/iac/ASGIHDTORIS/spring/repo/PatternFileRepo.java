package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;

import java.util.List;

public interface PatternFileRepo extends JpaRepository<PatternFile, Long> {

    PatternFile findById(long id);

    @Query(value = "SELECT * FROM pattern_file WHERE " +
            "pattern_id = :id" +
            " AND date_creation = " +
            "(SELECT min(date_creation) FROM pattern_file WHERE " +
            "pattern_id = :id)"
            , nativeQuery = true)
    PatternFile findByPatternIdAndDateCreationMin(@Param("id") long patternTableId);

    Integer countAllByPatternId(long id);

    boolean existsByPatternId(long patternId);

    List<PatternFile> findAllByPatternId(long id);

}
