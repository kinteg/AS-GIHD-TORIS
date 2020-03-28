package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.source.SourceDataModel;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

@Service
public interface SourceService {

    Page<Source> findAllSourceByQuery(Pageable pageable, Source source, SourceDataModel sourceDataModel, String key, String sort);

}
