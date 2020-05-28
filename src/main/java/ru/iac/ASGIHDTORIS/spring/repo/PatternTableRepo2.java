package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternTableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

@Repository
public interface PatternTableRepo2 {

    Page<PatternTable> findAllPatternTableByQuery(Pageable pageable, PatternTableModel patternTable);

}
