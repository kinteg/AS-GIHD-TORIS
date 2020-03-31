package ru.iac.ASGIHDTORIS.spring.component.Mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SourceMapper implements Mapper<List<Source>> {
    @Override
    public List<Source> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Source> values = new ArrayList<>();
        values.add(getSource(rs));

        while (rs.next()) {
            values.add(getSource(rs));
        }

        return values;

    }

    private Source getSource(ResultSet rs) throws SQLException {
        return Source.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .longName(rs.getString("long_name"))
                .shortName(rs.getString("short_name"))
                .description(rs.getString("description"))
                .addDescription(rs.getString("add_description"))
                .scope(rs.getString("scope"))
                .periodicity(rs.getString("periodicity"))
                .renewalPeriod(rs.getString("renewal_period"))
                .type(rs.getString("type"))
                .tags(rs.getString("tags"))
                .providerLink(rs.getString("provider_link"))
                .dataSource(rs.getString("data_source"))
                .dateCreation(rs.getTimestamp("date_creation") == null ? null : rs.getTimestamp("date_creation").toLocalDateTime())
                .dateDeactivation(rs.getTimestamp("date_deactivation") == null ? null : rs.getTimestamp("date_deactivation").toLocalDateTime())
                .dateActivation(rs.getTimestamp("date_activation") == null ? null : rs.getTimestamp("date_activation").toLocalDateTime())
                .lastUpdate(rs.getTimestamp("last_update") == null ? null : rs.getTimestamp("last_update").toLocalDateTime())
                .isArchive(rs.getBoolean("archive"))
                .build();
    }
}
