package ru.iac.ASGIHDTORIS.service.parser.json;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;

import java.util.List;

@Service
public interface JsonParser {

    String getFileName(String tableInfo);
    String getTableName(String tableInfo);
    List<DataModel> getModels(String tableInfo);

    String getFileName(String tableInfo, int position);
    String getTableName(String tableInfo, int position);
    List<DataModel> getModels(String tableInfo, int position);

}
