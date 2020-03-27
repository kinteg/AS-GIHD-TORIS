package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.time.LocalDateTime;

public interface SourceRepo extends JpaRepository<Source, Long> {
    Source findById(long sourceId);

    Source findByName(String sourceName);

    Page<Source> findAllByIsArchive(boolean isArchive, Pageable pageable);

    Page<Source> findAll(Pageable pageable);


    boolean existsById(long id);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM source " +
            " WHERE name LIKE %:name%" +
            " AND long_name LIKE %:longName%" +
            " AND short_name LIKE %:shortName%" +
            " AND description LIKE %:description%" +
            " AND add_description LIKE %:addDescription%" +
            " AND scope LIKE %:scope%" +
            " AND periodicity LIKE %:periodicity%" +
            " AND renewal_period LIKE %:renewalPeriod%" +
            " AND type LIKE %:type%" +
            " AND tags LIKE %:tags%" +
            " AND provider_link LIKE %:providerLink%" +
            " AND data_source LIKE %:dataSource%",
//            " AND is_arсhive = :isArchive" +
//            " AND (date_creation > :dateCreation1" +
//            " AND date_creation < :dateCreation2)" +
//            " AND (date_deactivation > :dateDeactivation1" +
//            " AND date_deactivation < :dateDeactivation2)" +
//            " AND (date_activation > :dateActivation1" +
//            " AND date_activation < :dateActivation2)" +
//            " AND (last_update > :lastUpdate1" +
//            " AND last_update < :lastUpdate2)",
            countQuery = "SELECT count(*) FROM source" +
                    " WHERE name LIKE %:name%" +
                    " AND long_name LIKE %:longName%" +
                    " AND short_name LIKE %:shortName%" +
                    " AND description LIKE %:description%" +
                    " AND add_description LIKE %:addDescription%" +
                    " AND scope LIKE %:scope%" +
                    " AND periodicity LIKE %:periodicity%" +
                    " AND renewal_period LIKE %:renewalPeriod%" +
                    " AND type LIKE %:type%" +
                    " AND tags LIKE %:tags%" +
                    " AND provider_link LIKE %:providerLink%" +
                    " AND data_source LIKE %:dataSource%",
//                    " AND is_arсhive = :isArchive" +
//                    " AND (date_creation > :dateCreation1" +
//                    " AND date_creation < :dateCreation2)" +
//                    " AND (date_deactivation > :dateDeactivation1" +
//                    " AND date_deactivation < :dateDeactivation2)" +
//                    " AND (date_activation > :dateActivation1" +
//                    " AND date_activation < :dateActivation2)" +
//                    " AND (last_update > :lastUpdate1" +
//                    " AND last_update < :lastUpdate2)",
            nativeQuery = true)
    Page<Source> findAllNative(
            Pageable pageable,
            @Param("name") String name,
            @Param("longName") String longName,
            @Param("shortName") String shortName,
            @Param("description") String description,
            @Param("addDescription") String addDescription,
            @Param("scope") String scope,
            @Param("periodicity") String periodicity,
            @Param("renewalPeriod") String renewalPeriod,
            @Param("type") String type,
            @Param("tags") String tags,
            @Param("providerLink") String providerLink,
            @Param("dataSource") String dataSource
//            @Param("dateCreation") LocalDateTime dateCreation,
//            @Param("dateCreation1") LocalDateTime dateCreation1,
//            @Param("dateCreation2") LocalDateTime dateCreation2,
//            @Param("dateDeactivation") LocalDateTime dateDeactivation,
//            @Param("dateDeactivation1") LocalDateTime dateDeactivation1,
//            @Param("dateDeactivation2") LocalDateTime dateDeactivation2,
//            @Param("dateActivation") LocalDateTime dateActivation,
//            @Param("dateActivation1") LocalDateTime dateActivation1,
//            @Param("dateActivation2") LocalDateTime dateActivation2,
//            @Param("lastUpdate") LocalDateTime lastUpdate,
//            @Param("lastUpdate1") LocalDateTime lastUpdate1,
//            @Param("lastUpdate2") LocalDateTime lastUpdate2,
//            @Param("isArchive") Boolean isArchive
    );

}