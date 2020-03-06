package ru.iac.ASGIHDTORIS.service.pattern;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.api.db.creator.Creator;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.service.parser.json.JsonParser;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class PatternCreatorServiceImpl implements PatternCreatorService {

    private final PatternRepo patternRepo;
    private final Creator creator;
    private final JsonParser jsonParser;
    private final PatternTableRepo patternTableRepo;

    public PatternCreatorServiceImpl(PatternRepo patternRepo, Creator creator, JsonParser jsonParser, PatternTableRepo patternTableRepo) {
        this.patternRepo = patternRepo;
        this.creator = creator;
        this.jsonParser = jsonParser;
        this.patternTableRepo = patternTableRepo;
    }

    @Override
    public String create(String json, Pattern pattern) {
        if (isValid(pattern)) {
            build(json, pattern);
            return "ok";
        }

        return "failed";
    }

    private boolean isValid(Pattern pattern) {
        String name = pattern.getName();
        return name != null &&
                !name.isEmpty() &&
                patternRepo
                        .findByName(name) == null;
    }

    private void build(String json, Pattern pattern) {
        int size = getSize(json);
        long id = createPattern(pattern, size).getId();
        for (int i = 0; i < size; i++) {
            String tableName = jsonParser.getTableName(json, i);
            String fileName = jsonParser.getFileName(json, i);
            List<DataModel> dataModels = jsonParser.getModels(json, i);

            creator.createTable(tableName, dataModels);
            createPatternTable(id, tableName, fileName);
        }
    }

    private int getSize(String json) {
        List<String> list = JsonPath.read(json, "$");
        return list.size();
    }

    private Pattern createPattern(Pattern pattern, int size) {
        pattern.setFileCount(String.valueOf(size));
        pattern.setDateCreation(LocalDateTime.now());

        return patternRepo.save(pattern);
    }

    private PatternTable createPatternTable(long id, String tableName, String fileName) {
        PatternTable patternTable = new PatternTable();

        patternTable.setPatternId(id);
        patternTable.setNameTable(tableName);
        patternTable.setNameFile(fileName);

        return patternTableRepo.save(patternTable);
    }

}
