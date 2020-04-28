package ru.iac.ASGIHDTORIS.spring.repo.impl.query.impl;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public interface QueryCreator<T> {

    void makeQuery(T object, Pageable pageable);

    String getCountQuery();

    String getSelectQuery();

}
