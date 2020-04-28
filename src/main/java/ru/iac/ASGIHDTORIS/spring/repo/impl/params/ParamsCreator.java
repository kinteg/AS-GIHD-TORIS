package ru.iac.ASGIHDTORIS.spring.repo.impl.params;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

@Component
public interface ParamsCreator<T> {

    MapSqlParameterSource makeParams(T object);

}
