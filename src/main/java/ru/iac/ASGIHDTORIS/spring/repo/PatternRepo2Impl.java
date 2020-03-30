package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.service.pattern.PatternMapper;
import ru.iac.ASGIHDTORIS.spring.service.source.SourceCountMapper;
import ru.iac.ASGIHDTORIS.spring.service.source.SourceMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class PatternRepo2Impl implements PatternRepo2 {
    private final String SELECT_QUERY = "SELECT * FROM source";
    private final String PAGE_QUERY = "SELECT count(*) FROM source";

    private final PatternMapper patternMapper;
    private final SourceCountMapper sourceCountMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public PatternRepo2Impl(PatternMapper patternMapper, SourceCountMapper sourceCountMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.patternMapper = patternMapper;
        this.sourceCountMapper = sourceCountMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Pattern> findAllSourceByQuery(Pageable pageable, PatternModel pattern) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(pattern, params);
        String pageQuery = createPageQuery(pageable, pattern.getSort(), pattern.getKey());
        String selectQuery = SELECT_QUERY + valueQuery + pageQuery;
        String pageableQuery = PAGE_QUERY + valueQuery;

        List<Pattern> sources = getAll(selectQuery, params);
        int count = getCount(pageableQuery, params);

        return new PageImpl<>(sources, pageable, count);
    }

    private String createQueryValue(PatternModel pattern, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (pattern.getId() != null  && !pattern.getId().equals("")) {
            values.add(" cast(id as text) ILIKE :id");
            params.addValue("id", "%" + pattern.getId() + "%");
        }
        if (pattern.getSourceId() != null  && !pattern.getSourceId().equals("")) {
            values.add(" source_id = :sourceId");
            params.addValue("sourceId",  + pattern.getSourceId());
        }
        if (pattern.getDescription() != null  && !pattern.getDescription().equals("")) {
            values.add(" description ILIKE :description ");
            params.addValue("description", "%" + pattern.getDescription() + "%");
        }
        if (pattern.getDirection() != null  && !pattern.getDirection().equals("")) {
            values.add(" direction ILIKE :direction");
            params.addValue("direction", "%" + pattern.getDirection() + "%");
        }
        if (pattern.getManagement() != null  && !pattern.getManagement().equals("")) {
            values.add(" management ILIKE :management");
            params.addValue("management", "%" + pattern.getManagement() + "%");
        }
        if (pattern.getName() != null  && !pattern.getName().equals("")) {
            values.add(" name ILIKE :name");
            params.addValue("name", "%" + pattern.getName() + "%");
        }
        if (pattern.getDateCreation1() != null) {
            values.add(" date_creation >= :dateCreation1");
            params.addValue("dateCreation1", pattern.getDateCreation1().atTime(0, 0, 0));
        }
        if (pattern.getDateCreation2() != null) {
            values.add(" date_creation <= :dateCreation2");
            params.addValue("dateCreation2", pattern.getDateCreation2().atTime(23, 59, 59));
        }
        if (pattern.getDateDeactivation1() != null) {
            values.add(" date_deactivation >= :dateDeactivation1");
            params.addValue("dateDeactivation1", pattern.getDateDeactivation1().atTime(0, 0, 0));
        }
        if (pattern.getDateDeactivation2() != null) {
            values.add(" date_deactivation <= :dateDeactivation2");
            params.addValue("dateDeactivation2", pattern.getDateDeactivation2().atTime(23, 59, 59));
        }
        if (pattern.getDateActivation1() != null) {
            values.add(" date_activation >= :dateActivation1");
            params.addValue("dateActivation1", pattern.getDateActivation1().atTime(0, 0, 0));
        }
        if (pattern.getDateActivation2() != null) {
            values.add(" date_activation <= :dateActivation2");
            params.addValue("dateActivation2", pattern.getDateActivation2().atTime(23, 59, 59));
        }
        if (pattern.getLastUpdate1() != null) {
            values.add(" last_update >= :lastUpdate1");
            params.addValue("lastUpdate1", pattern.getLastUpdate1().atTime(0, 0, 0));
        }
        if (pattern.getLastUpdate2() != null) {
            values.add(" last_update <= :lastUpdate2");
            params.addValue("lastUpdate2", pattern.getLastUpdate2().atTime(23, 59, 59));
        }
        if (pattern.getFileCount1() != null) {
            values.add(" file_count >= :fileCount1");
            params.addValue("fileCount1", pattern.getFileCount1());
        }
        if (pattern.getFileCount2() != null) {
            values.add(" file_count <= :fileCount2");
            params.addValue("fileCount2", pattern.getFileCount2());
        }
        if (pattern.getIsArchive() != null) {
            values.add(" archive = :isArchive");
            params.addValue("isArchive", pattern.getIsArchive());
        }

        return (values.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", values);
    }

    private String createPageQuery(Pageable pageable, String sort, String key) {
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

    private List<Pattern> getAll(String query, MapSqlParameterSource params) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            query,
                            params,
                            patternMapper
                    )
            ).get();
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    private Integer getCount(String query, MapSqlParameterSource params) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            query,
                            params,
                            sourceCountMapper
                    )
            ).get();
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }
}
