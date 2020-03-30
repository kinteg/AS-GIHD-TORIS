package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SourceCountMapper implements RowMapper<Integer> {
    @Override
    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt(1);
    }
}
