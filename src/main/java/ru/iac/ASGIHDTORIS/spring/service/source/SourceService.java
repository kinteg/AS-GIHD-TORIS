package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Service
public interface SourceService {

    Source createSource(Source source, User user);

    Source archiveSource(Long id, User user);

    Source deArchiveSource(Long id, User user);

    Source updateSource(Source source, User user);

}
