package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

@Service
public interface SourceService {

    Source createSource(Source source);

    Source archiveSource(Long id);

    Source deArchiveSource(Long id);

    Source updateSource(@ModelAttribute Source source);

}
