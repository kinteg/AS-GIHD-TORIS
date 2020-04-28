package ru.iac.ASGIHDTORIS.spring.repo.impl.query.page;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface PageQueryCreator {

    String createPageQuery(Pageable pageable, String sort, String key, String customKey);

}
