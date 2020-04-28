package ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.repo.impl.query.PageQueryCreator;

//TODO придумать чё-нить
@Component
public class PageQueryCreatorImpl implements PageQueryCreator {
    @Override
    public String createPageQuery(Pageable pageable, String sort, String key, String customKey) {
        if (key == null || key.isEmpty()) {
            key = customKey;
        }

        if (sort == null || sort.isEmpty()) {
            sort = "asc";
        }

        return " ORDER BY " + key + " " + sort +
                " LIMIT " + pageable.getPageSize() +
                " OFFSET " + pageable.getPageSize() * pageable.getPageNumber();
    }
}
