package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.time.LocalDate;

public interface SourceRepo extends JpaRepository<Source, Long> {

    String query = " WHERE " +
            " cast(id as text) LIKE %:id%" +
            " AND name LIKE %:name%" +
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
            " AND data_source LIKE %:dataSource%" +
            " AND date_creation >= :dateCreation1" +
            " AND date_creation <= :dateCreation2" +
            " AND date_activation >= :dateActivation1" +
            " AND date_activation <= :dateActivation2" +
            " AND last_update >= :lastUpdate1" +
            " AND last_update <= :lastUpdate2";

    @Query(value = "SELECT * FROM source " +
            " AND date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation < :dateDeactivation2" + query,
            countQuery = "SELECT count(*) FROM source " +
                    " AND date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation < :dateDeactivation2" + query,
            nativeQuery = true)
    Page<Source> findAllSourceByQuery(
            Pageable pageable,
            @Param("id") String id,
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
            @Param("dataSource") String dataSource,
            @Param("dateCreation1") LocalDate dateCreation1,
            @Param("dateCreation2") LocalDate dateCreation2,
            @Param("dateDeactivation1") LocalDate dateDeactivation1,
            @Param("dateDeactivation2") LocalDate dateDeactivation2,
            @Param("dateActivation1") LocalDate dateActivation1,
            @Param("dateActivation2") LocalDate dateActivation2,
            @Param("lastUpdate1") LocalDate lastUpdate1,
            @Param("lastUpdate2") LocalDate lastUpdate2
    );

    @Query(value = "SELECT * FROM source " +
            " AND date_deactivation >= :dateDeactivation1" +
            " AND date_deactivation < :dateDeactivation2" +
            query +
            " AND is_arсhive = :isArchive",
            countQuery = "SELECT count(*) FROM source " +
                    " AND date_deactivation >= :dateDeactivation1" +
                    " AND date_deactivation < :dateDeactivation2" +
                    query +
                    " AND is_arсhive = :isArchive",
            nativeQuery = true)
    Page<Source> findAllSourceByQuery(
            Pageable pageable,
            @Param("id") String id,
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
            @Param("dataSource") String dataSource,
            @Param("dateCreation1") LocalDate dateCreation1,
            @Param("dateCreation2") LocalDate dateCreation2,
            @Param("dateDeactivation1") LocalDate dateDeactivation1,
            @Param("dateDeactivation2") LocalDate dateDeactivation2,
            @Param("dateActivation1") LocalDate dateActivation1,
            @Param("dateActivation2") LocalDate dateActivation2,
            @Param("lastUpdate1") LocalDate lastUpdate1,
            @Param("lastUpdate2") LocalDate lastUpdate2,
            @Param("isArchive") Boolean isArchive
    );

    @Query(value = "SELECT * FROM source " + query,
            countQuery = "SELECT count(*) FROM source " + query,
            nativeQuery = true)
    Page<Source> findAllSourceByQuery(
            Pageable pageable,
            @Param("id") String id,
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
            @Param("dataSource") String dataSource,
            @Param("dateCreation1") LocalDate dateCreation1,
            @Param("dateCreation2") LocalDate dateCreation2,
            @Param("dateActivation1") LocalDate dateActivation1,
            @Param("dateActivation2") LocalDate dateActivation2,
            @Param("lastUpdate1") LocalDate lastUpdate1,
            @Param("lastUpdate2") LocalDate lastUpdate2
    );

    @Query(value = "SELECT * FROM source " + query +
            " AND is_arсhive = :isArchive",
            countQuery = "SELECT count(*) FROM source " + query +
                    " AND is_arсhive = :isArchive",
            nativeQuery = true)
    Page<Source> findAllSourceByQuery(
            Pageable pageable,
            @Param("id") String id,
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
            @Param("dataSource") String dataSource,
            @Param("dateCreation1") LocalDate dateCreation1,
            @Param("dateCreation2") LocalDate dateCreation2,
            @Param("dateActivation1") LocalDate dateActivation1,
            @Param("dateActivation2") LocalDate dateActivation2,
            @Param("lastUpdate1") LocalDate lastUpdate1,
            @Param("lastUpdate2") LocalDate lastUpdate2,
            @Param("isArchive") Boolean isArchive
    );

    Source findById(long sourceId);

    Page<Source> findAllByIsArchive(boolean isArchive, Pageable pageable);

    Page<Source> findAll(Pageable pageable);

    boolean existsById(String id);


}