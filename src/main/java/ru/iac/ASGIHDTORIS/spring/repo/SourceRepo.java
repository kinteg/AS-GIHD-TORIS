package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.time.LocalDateTime;

public interface SourceRepo extends JpaRepository<Source, Long> {
    Source findById(long sourceId);
    Source findByName(String sourceName);

    Page<Source> findAllByIsArchive(boolean isArchive, Pageable pageable);
    Page<Source> findAll(Pageable pageable);


    boolean existsById(long id);

    boolean existsByName(String name);

    @Query(
            value = "SELECT s FROM source s WHERE " +
                    "s.name LIKE '%?2%'",
            nativeQuery = true
    )
    Page<Source> findAllSourceWithPagination(
            Pageable pageable,
            String name
//            String longName,
//            String shortName,
//            String description,
//            String addDescription,
//            String scope,
//            String periodicity,
//            String renewalPeriod,
//            String type,
//            String tags,
//            String providerLink,
//            String dataSource,
//            LocalDateTime dateCreation,
//            LocalDateTime dateCreation2,
//            LocalDateTime dateDeactivation,
//            LocalDateTime dateDeactivation2,
//            LocalDateTime dateActivation,
//            LocalDateTime dateActivation2,
//            LocalDateTime lastUpdate,
//            LocalDateTime lastUpdate2,
//            Boolean isArchive
            );

}