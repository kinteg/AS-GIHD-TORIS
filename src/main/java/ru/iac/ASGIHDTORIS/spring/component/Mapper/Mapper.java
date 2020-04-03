package ru.iac.ASGIHDTORIS.spring.component.Mapper;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface Mapper<T> extends RowMapper<T> {

    void setKeys(List<String> keys);

}
