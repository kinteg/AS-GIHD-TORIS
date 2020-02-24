package ru.iac.ASGIHDTORIS.service.parser.json;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.List;

@Service
public interface JsonParser {

    String getFileName(String tableInfo);
    String getTableName(String tableInfo);
    List<DataModel> getModels(String tableInfo);

}
