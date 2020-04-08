package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;

import java.util.List;

public interface PatternTableFileRepo extends JpaRepository<PatternTableFile, Long> {

    PatternTableFile findById(long id);

    @Query(value = "SELECT * FROM pattern_table_file WHERE " +
            "pattern_table_id = :id" +
            " AND date_creation = " +
            "(SELECT min(date_creation) FROM pattern_table_file WHERE " +
            "pattern_table_id = :id)"
            , nativeQuery = true)
    PatternTableFile findByPatternTableIdAndDateCreationMin(@Param("id") long patternTableId);

    Integer countAllByPatternTableId(long id);

    boolean existsByPatternTableId(long patternId);

    List<PatternTableFile> findAllByPatternTableId(long id);

}
