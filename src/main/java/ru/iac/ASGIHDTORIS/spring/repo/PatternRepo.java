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

    Page<Pattern> findAll(Pageable pageable);

    Page<Pattern> findAllByIsArchive(Boolean isArchive, Pageable pageable);

    Page<Pattern> findAllBySourceIdAndIsArchive(long sourceId, boolean isArchive, Pageable pageable);

    Pattern findById(long id);

    boolean existsById(long id);

    boolean existsBySourceId(long id);

    String query = " WHERE " +
            " cast(id as text) LIKE %:id%" +
            " AND name LIKE %:name%" +
            " AND description LIKE %:description%" +
            " AND direction LIKE %:direction%" +
            " AND management LIKE %:management%" +
            " AND (file_count <= :fileCount1" +
            " AND file_count >= :fileCount2)" +
            " AND (date_creation >= :dateCreation1" +
            " AND date_creation <= :dateCreation2)" +
            " AND (date_activation >= :dateActivation1" +
            " AND date_activation <= :dateActivation2)" +
            " AND (last_update >= :lastUpdate1" +
            " AND last_update <= :lastUpdate2)";

    @Query(value = "SELECT * FROM pattern " + query,
            countQuery = "SELECT count(*) FROM pattern " + query,
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2
    );

    @Query(value = "SELECT * FROM pattern " + query +
            " AND is_arсhive = :isArchive",
            countQuery = "SELECT count(*) FROM pattern " + query +
                    " AND is_arсhive = :isArchive",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("isArchive") Boolean isArchive
    );

    @Query(value = "SELECT * FROM pattern "
            + query +
            " AND source_id = :sourceId",
            countQuery = "SELECT count(*) FROM pattern "
                    + query +
                    " AND source_id = :sourceId",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("sourceId") Long sourceId
    );

    @Query(value = "SELECT * FROM pattern " + query +
            " AND is_arсhive = :isArchive" +
            " AND source_id = :sourceId",
            countQuery = "SELECT count(*) FROM pattern " + query +
                    " AND is_arсhive = :isArchive" +
                    "  AND source_id = :sourceId",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("isArchive") Boolean isArchive,
            @Param("sourceId") Long sourceId
    );

    @Query(value = "SELECT * FROM pattern " + query +
            " AND (date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation <= :dateDeactivation2)",
            countQuery = "SELECT count(*) FROM pattern " + query + query +
                    " AND (date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation <= :dateDeactivation2)",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateDeactivation1") LocalDateTime dateDeactivation1,
            @Param("dateDeactivation2") LocalDateTime dateDeactivation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2
    );

    @Query(value = "SELECT * FROM pattern " + query +
            " AND is_arсhive = :isArchive" + query +
            " AND (date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation <= :dateDeactivation2)",
            countQuery = "SELECT count(*) FROM pattern " + query +
                    " AND is_arсhive = :isArchive" + query +
                    " AND (date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation <= :dateDeactivation2)",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateDeactivation1") LocalDateTime dateDeactivation1,
            @Param("dateDeactivation2") LocalDateTime dateDeactivation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("isArchive") Boolean isArchive
    );

    @Query(value = "SELECT * FROM pattern "
            + query +
            " AND source_id = :sourceId" + query +
            " AND (date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation <= :dateDeactivation2)",
            countQuery = "SELECT count(*) FROM pattern "
                    + query +
                    " AND source_id = :sourceId" + query +
                    " AND (date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation <= :dateDeactivation2)",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateDeactivation1") LocalDateTime dateDeactivation1,
            @Param("dateDeactivation2") LocalDateTime dateDeactivation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("sourceId") Long sourceId
    );

    @Query(value = "SELECT * FROM pattern " + query +
            " AND is_arсhive = :isArchive" +
            " AND source_id = :sourceId" + query +
            " AND (date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation <= :dateDeactivation2)",
            countQuery = "SELECT count(*) FROM pattern " + query +
                    " AND is_arсhive = :isArchive" +
                    "  AND source_id = :sourceId" + query +
                    " AND (date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation <= :dateDeactivation2)",
            nativeQuery = true)
    Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            @Param("id") String id,
            @Param("name") String name,
            @Param("description") String description,
            @Param("direction") String direction,
            @Param("management") String management,
            @Param("fileCount1") Integer fileCount1,
            @Param("fileCount2") Integer fileCount2,
            @Param("dateCreation1") LocalDateTime dateCreation1,
            @Param("dateCreation2") LocalDateTime dateCreation2,
            @Param("dateDeactivation1") LocalDateTime dateDeactivation1,
            @Param("dateDeactivation2") LocalDateTime dateDeactivation2,
            @Param("dateActivation1") LocalDateTime dateActivation1,
            @Param("dateActivation2") LocalDateTime dateActivation2,
            @Param("lastUpdate1") LocalDateTime lastUpdate1,
            @Param("lastUpdate2") LocalDateTime lastUpdate2,
            @Param("isArchive") Boolean isArchive,
            @Param("sourceId") Long sourceId
    );

}
