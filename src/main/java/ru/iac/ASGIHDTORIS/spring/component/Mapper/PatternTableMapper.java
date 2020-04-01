package ru.iac.ASGIHDTORIS.spring.component.Mapper;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PatternTableMapper implements Mapper<List<PatternTable>> {
    @Override
    public List<PatternTable> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<PatternTable> values = new ArrayList<>();
        values.add(getSource(rs));

        while (rs.next()) {
            values.add(getSource(rs));
        }

        return values;

    }

    private PatternTable getSource(ResultSet rs) throws SQLException {
        return PatternTable.builder()
                .id(rs.getLong("id"))
                .nameFile(rs.getString("name_file"))
                .nameTable(rs.getString("name_table"))
                .patternId(rs.getLong("pattern_id"))
                .patternId(rs.getLong("source_id"))
                .dateCreation(rs.getTimestamp("date_creation") == null ? null : rs.getTimestamp("date_creation").toLocalDateTime())
                .dateDeactivation(rs.getTimestamp("date_deactivation") == null ? null : rs.getTimestamp("date_deactivation").toLocalDateTime())
                .dateActivation(rs.getTimestamp("date_activation") == null ? null : rs.getTimestamp("date_activation").toLocalDateTime())
                .lastUpdate(rs.getTimestamp("last_update") == null ? null : rs.getTimestamp("last_update").toLocalDateTime())
                .isArchive(rs.getBoolean("archive"))
                .build();
    }
}
