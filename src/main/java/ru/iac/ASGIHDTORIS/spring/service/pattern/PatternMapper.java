package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatternMapper implements RowMapper<List<Pattern>> {
    @Override
    public List<Pattern> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Pattern> values = new ArrayList<>();
        values.add(getSource(rs));

        while (rs.next()) {
            values.add(getSource(rs));
        }

        return values;

    }

    private Pattern getSource(ResultSet rs) throws SQLException {
        return Pattern.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .direction(rs.getString("direction"))
                .management(rs.getString("management"))
                .sourceId(rs.getLong("source_id"))
                .fileCount(rs.getInt("file_count"))
                .dateCreation(rs.getTimestamp("date_creation") == null ? null : rs.getTimestamp("date_creation").toLocalDateTime())
                .dateDeactivation(rs.getTimestamp("date_deactivation") == null ? null : rs.getTimestamp("date_deactivation").toLocalDateTime())
                .dateActivation(rs.getTimestamp("date_activation") == null ? null : rs.getTimestamp("date_activation").toLocalDateTime())
                .lastUpdate(rs.getTimestamp("last_update") == null ? null : rs.getTimestamp("last_update").toLocalDateTime())
                .isArchive(rs.getBoolean("is_archive"))
                .build();
    }
}
