package ru.iac.ASGIHDTORIS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.domain.Source;

import java.util.Optional;

public interface SourceRepo extends JpaRepository<Source, Long> {
    Source findById(long sourceId);
    Source findByName(String sourceName);
}