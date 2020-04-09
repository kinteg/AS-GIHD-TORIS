package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

public interface SourceRepo extends JpaRepository<Source, Long> {

    Source findById(long sourceId);

    Page<Source> findAllByIsArchive(boolean isArchive, Pageable pageable);

    Page<Source> findAll(Pageable pageable);

    boolean existsByShortName(String name);

    boolean existsByShortNameAndId(String name, long id);

}