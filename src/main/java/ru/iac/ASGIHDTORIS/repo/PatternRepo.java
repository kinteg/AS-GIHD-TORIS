package ru.iac.ASGIHDTORIS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.domain.Pattern;

import java.util.List;

public interface PatternRepo  extends JpaRepository<Pattern, Long> {
    List<Pattern> findBySourceId(Long id);
}
