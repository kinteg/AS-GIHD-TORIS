package ru.iac.ASGIHDTORIS.service.exporer;

import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.api.db.exporter.parser.DbParser;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DbExporterService implements DbParserService {

    private final PatternTableRepo patternTableRepo;
    private final DbParser dbParser;

    public DbExporterService(PatternTableRepo patternTableRepo, DataSource dataSource, DbParser dbParser) {
        this.patternTableRepo = patternTableRepo;
        this.dbParser = dbParser;
    }

    @Override
    public String getData(Long patternId, long limit) {
        return exportData(patternId, limit).toJSONString();
    }

    private JSONObject exportData(Long patternId, long limit) {
        return dbParser.getFromDb(getTableNames(patternId), limit);
    }

    private List<String> getTableNames(Long patternId) {
        return getPatternTables(patternId)
                .stream()
                .map(PatternTable::getNameTable)
                .collect(Collectors.toList());
    }

    private List<PatternTable> getPatternTables(Long patternId) {
        return patternTableRepo.findByPatternId(patternId);
    }

}
