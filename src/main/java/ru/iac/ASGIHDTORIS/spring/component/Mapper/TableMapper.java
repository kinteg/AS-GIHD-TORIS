package ru.iac.ASGIHDTORIS.spring.component.Mapper;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class TableMapper implements Mapper<List<Map<String, String>>> {

    private List<String> columns;

    @Override
    public List<Map<String, String>> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Map<String, String>> values = new ArrayList<>();
        values.add(getTable(rs));

        while (rs.next()) {
            values.add(getTable(rs));
        }

        return values;
    }

    private Map<String, String> getTable(ResultSet rs) throws SQLException {
        Map<String, String> values = new LinkedHashMap<>();

        for (String key :
                columns) {
            values.put(key, rs.getString(key));
        }

        return values;
    }

    @Override
    public void setKeys(List<String> keys) {
        columns = keys;
    }

}
