package ru.iac.ASGIHDTORIS.spring.component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
public class FullRepoHelper<T> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public FullRepoHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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

    public List<T> getAll(String selectQuery, MapSqlParameterSource params, Mapper<List<T>> mapper) {
        log.info(selectQuery);
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            selectQuery,
                            params,
                            mapper
                    )
            ).orElse(Collections.emptyList());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Integer getCount(String countQuery, MapSqlParameterSource params, CountMapper countMapper) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            countQuery,
                            params,
                            countMapper
                    )
            ).orElse(0);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}
