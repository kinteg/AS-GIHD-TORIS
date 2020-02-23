package ru.iac.ASGIHDTORIS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.domain.Table;

import java.util.List;

public interface TableRepo  extends JpaRepository<Table,Long> {
    List<Table> findByPatternId(long id);
}
