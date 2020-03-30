package ru.iac.ASGIHDTORIS.spring.service.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.domain.SourceModel;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class SourceServiceImpl2 implements SourceService {

    private final String SELECT_QUERY = "SELECT * FROM source";
    private final String PAGE_QUERY = "SELECT count(*) FROM source";

    private final SourceMapper sourceMapper;
    private final SourceCountMapper sourceCountMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public SourceServiceImpl2(SourceMapper sourceMapper, SourceCountMapper sourceCountMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.sourceMapper = sourceMapper;
        this.sourceCountMapper = sourceCountMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<Source> findAllSourceByQuery(Pageable pageable, SourceModel source) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String valueQuery = createQueryValue(source, params);
        String pageQuery = createPageQuery(pageable, source.getSort(), source.getKey());
        String selectQuery = SELECT_QUERY + valueQuery + pageQuery;
        String pageableQuery = PAGE_QUERY + valueQuery;

        List<Source> sources = getAll(selectQuery, params);
        int count = getCount(pageableQuery, params);

        return new PageImpl<>(sources, pageable, count);
    }

    private String createQueryValue(SourceModel source, MapSqlParameterSource params) {
        List<String> values = new ArrayList<>();

        if (source.getId() != null  && !source.getId().equals("")) {
            values.add(" cast(id as text) LIKE :id");
            params.addValue("id", "%" + source.getId() + "%");
        }
        if (source.getName() != null  && !source.getName().equals("")) {
            values.add(" name LIKE :name ");
            params.addValue("name", "%" + source.getName().toLowerCase() + "%");
        }
        if (source.getLongName() != null  && !source.getLongName().equals("")) {
            values.add(" long_name LIKE longName");
            params.addValue("longName", "%" + source.getLongName().toLowerCase() + "%");
        }
        if (source.getShortName() != null  && !source.getShortName().equals("")) {
            values.add(" short_name LIKE shortName");
            params.addValue("shortName", "%" + source.getShortName().toLowerCase() + "%");
        }
        if (source.getDescription() != null  && !source.getDescription().equals("")) {
            values.add(" description LIKE description");
            params.addValue("description", "%" + source.getDescription().toLowerCase() + "%");
        }
        if (source.getAddDescription() != null  && !source.getAddDescription().equals("")) {
            values.add(" add_description LIKE addDescription");
            params.addValue("addDescription", "%" + source.getAddDescription().toLowerCase() + "%");
        }
        if (source.getScope() != null  && !source.getScope().equals("")) {
            values.add(" scope LIKE scope");
            params.addValue("scope", "%" + source.getScope().toLowerCase() + "%");
        }
        if (source.getPeriodicity() != null  && !source.getPeriodicity().equals("")) {
            values.add(" periodicity LIKE periodicity");
            params.addValue("periodicity", "%" + source.getPeriodicity().toLowerCase() + "%");
        }
        if (source.getRenewalPeriod() != null  && !source.getRenewalPeriod().equals("")) {
            values.add(" renewal_period LIKE renewalPeriod");
            params.addValue("renewalPeriod", "%" + source.getRenewalPeriod().toLowerCase() + "%");
        }
        if (source.getType() != null  && !source.getType().equals("")) {
            values.add(" type LIKE type");
            params.addValue("type", "%" + source.getType().toLowerCase() + "%");
        }
        if (source.getTags() != null  && !source.getTags().equals("")) {
            values.add(" tags LIKE tags");
            params.addValue("tags", "%" + source.getTags().toLowerCase() + "%");
        }
        if (source.getProviderLink() != null  && !source.getProviderLink().equals("")) {
            values.add(" provider_link LIKE providerLink");
            params.addValue("providerLink", "%" + source.getProviderLink().toLowerCase() + "%");
        }
        if (source.getDataSource() != null  && !source.getDataSource().equals("")) {
            values.add(" data_source LIKE dataSource");
            params.addValue("dataSource", "%" + source.getDataSource().toLowerCase() + "%");
        }
        if (source.getDateCreation1() != null) {
            values.add(" date_creation >= :dateCreation1");
            params.addValue("dateCreation1", source.getDateCreation1().atTime(0, 0, 0));
        }
        if (source.getDateCreation2() != null) {
            values.add(" date_creation <= :dateCreation2");
            params.addValue("dateCreation2", source.getDateCreation2().atTime(23, 59, 59));
        }
        if (source.getDateDeactivation1() != null) {
            values.add(" date_deactivation >= :dateDeactivation1");
            params.addValue("dateDeactivation1", source.getDateDeactivation1().atTime(0, 0, 0));
        }
        if (source.getDateDeactivation2() != null) {
            values.add(" date_deactivation <= :dateDeactivation2");
            params.addValue("dateDeactivation2", source.getDateDeactivation2().atTime(23, 59, 59));
        }
        if (source.getDateActivation1() != null) {
            values.add(" date_activation >= :dateActivation1");
            params.addValue("dateActivation1", source.getDateActivation1().atTime(0, 0, 0));
        }
        if (source.getDateActivation2() != null) {
            values.add(" date_activation <= :dateActivation2");
            params.addValue("dateActivation2", source.getDateActivation2().atTime(23, 59, 59));
        }
        if (source.getLastUpdate1() != null) {
            values.add(" last_update >= :lastUpdate1");
            params.addValue("lastUpdate1", source.getLastUpdate1().atTime(0, 0, 0));
        }
        if (source.getLastUpdate2() != null) {
            values.add(" last_update <= :lastUpdate2");
            params.addValue("lastUpdate2", source.getLastUpdate2().atTime(23, 59, 59));
        }
        if (source.getIsArchive() != null) {
            values.add(" archive = :isArchive");
            params.addValue("isArchive", source.getIsArchive());
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

    private List<Source> getAll(String query, MapSqlParameterSource params) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            query,
                            params,
                            sourceMapper
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
