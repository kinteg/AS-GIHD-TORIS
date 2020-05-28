package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.SourceSet;

import java.util.List;

public interface SourceSetRepo extends JpaRepository<SourceSet, Long> {

    boolean existsBySourceIdAndUserId(Long sourceId, Long userId);

    List<SourceSet> findAllBySourceId(Long sourceId);

    List<SourceSet> findAllBySourceIdNot(Long sourceId);

    void deleteBySourceIdAndUserId(Long sourceId, Long userId);

    SourceSet findBySourceIdAndUserId(Long sourceId, Long userId);

}
