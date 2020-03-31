package ru.iac.ASGIHDTORIS.spring.component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.domain.HelpModel;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@Data
public class FullRepoHelper<T> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_QUERY = "SELECT * FROM source";
    private final String PAGE_QUERY = "SELECT count(*) FROM source";
    
    private List<String> values = Collections.emptyList();
    private MapSqlParameterSource params = new MapSqlParameterSource();

    public FullRepoHelper(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createDataQuery(HelpModel helpModel) {
        values = new ArrayList<>();
        params = new MapSqlParameterSource();
        
        if (helpModel != null) {
            if (helpModel.getDateCreation1() != null) {
                values.add(" date_creation >= :dateCreation1");
                params.addValue("dateCreation1", helpModel.getDateCreation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateCreation2() != null) {
                values.add(" date_creation <= :dateCreation2");
                params.addValue("dateCreation2", helpModel.getDateCreation2().atTime(23, 59, 59));
            }
            if (helpModel.getDateDeactivation1() != null) {
                values.add(" date_deactivation >= :dateDeactivation1");
                params.addValue("dateDeactivation1", helpModel.getDateDeactivation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateDeactivation2() != null) {
                values.add(" date_deactivation <= :dateDeactivation2");
                params.addValue("dateDeactivation2", helpModel.getDateDeactivation2().atTime(23, 59, 59));
            }
            if (helpModel.getDateCreation1() != null) {
                values.add(" date_activation >= :dateActivation1");
                params.addValue("dateActivation1", helpModel.getDateCreation1().atTime(0, 0, 0));
            }
            if (helpModel.getDateActivation2() != null) {
                values.add(" date_activation <= :dateActivation2");
                params.addValue("dateActivation2", helpModel.getDateActivation2().atTime(23, 59, 59));
            }
            if (helpModel.getLastUpdate1() != null) {
                values.add(" last_update >= :lastUpdate1");
                params.addValue("lastUpdate1", helpModel.getLastUpdate1().atTime(0, 0, 0));
            }
            if (helpModel.getLastUpdate2() != null) {
                values.add(" last_update <= :lastUpdate2");
                params.addValue("lastUpdate2", helpModel.getLastUpdate2().atTime(23, 59, 59));
            }
            if (helpModel.getIsArchive() != null) {
                values.add(" archive = :isArchive");
                params.addValue("isArchive", helpModel.getIsArchive());
            }
        }
    }

    public String createPageQuery(Pageable pageable, String sort, String key) {
        if (key == null || key.isEmpty()) {
            key = "id";
        }

        if (sort == null || sort.isEmpty()) {
            sort = "asc";
        }

        return " ORDER BY " + key + " " + sort +
                " LIMIT " + pageable.getPageSize() +
                " OFFSET " + pageable.getPageSize() * pageable.getPageNumber();
    }

    public String getSelectQuery(String valueQuery, String pageQuery) {
        return SELECT_QUERY + valueQuery + pageQuery;
    }

    public String getCountQuery(String valueQuery) {
        return PAGE_QUERY + valueQuery;
    }


    public List<T> getAll(String valueQuery, String pageQuery, MapSqlParameterSource params, Mapper<List<T>> mapper) {

        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            getSelectQuery(valueQuery, pageQuery),
                            params,
                            mapper
                    )
            ).orElse(Collections.emptyList());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    public Integer getCount(String valueQuery, MapSqlParameterSource params, SourceCountMapper sourceCountMapper) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            getCountQuery(valueQuery),
                            params,
                            sourceCountMapper
                    )
            ).orElse(0);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}
