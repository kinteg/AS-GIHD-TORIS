package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

@Repository
public interface PatternRepo2 {

    Page<Pattern> findAllSourceByQuery(Pageable pageable, PatternModel pattern);

}
