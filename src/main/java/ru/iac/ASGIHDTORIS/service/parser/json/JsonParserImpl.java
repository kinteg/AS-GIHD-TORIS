package ru.iac.ASGIHDTORIS.service.parser.json;

import com.jayway.jsonpath.JsonPath;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModelCreator;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class JsonParserImpl implements JsonParser {

    private final String QUERY = "$.content.${item}";
    private final String BIG_QUERY = "$.[${i}].content.${item}";
    private final String COLUMN_TABLE_QUERY = "$.content.columnTable[${i}].${key}";
    private final String BIG_COLUMN_TABLE_QUERY = "$.[${i}].content.columnTable[${i}].${key}";

    private final String ITERATOR_REGEX = "\\$\\{i\\}";
    private final String KEY_REGEX = "\\$\\{key\\}";
    private final String ITEM_REGEX = "\\$\\{item\\}";

    private final String FILE_NAME = "nameFile";
    private final String TABLE_NAME = "nameTable";
    private final String COLUMNS_TABLE = "columnTable";

    private final String NAME = "name";
    private final String TYPE = "type";
    private final String PRIMARY = "primary";

    private final DataModelCreator dataModelCreator;

    public JsonParserImpl(DataModelCreator dataModelCreator) {
        this.dataModelCreator = dataModelCreator;
    }

    @Override
    public String getFileName(String tableInfo) {
        String query = QUERY.replaceFirst(ITEM_REGEX, FILE_NAME);
        return JsonPath.read(tableInfo, query);
    }

    @Override
    public String getFileName(String tableInfo, int position) {
        String query = BIG_QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(position));
        query = query.replaceFirst(ITEM_REGEX, FILE_NAME);
        return JsonPath.read(tableInfo, query);
    }

    @Override
    public String getTableName(String tableInfo) {
        String query = QUERY.replaceFirst(ITEM_REGEX, TABLE_NAME);
        return JsonPath.read(tableInfo, query);
    }

    @Override
    public String getTableName(String tableInfo, int position) {
        String query = BIG_QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(position));
        query = query.replaceFirst(ITEM_REGEX, TABLE_NAME);
        return JsonPath.read(tableInfo, query);
    }

    @Override
    public List<DataModel> getModels(String tableInfo) {
        String columnTable = QUERY.replaceFirst(ITEM_REGEX, COLUMNS_TABLE);
        List<String> columns = JsonPath.read(tableInfo, columnTable);

        String modelQuery = createColumnTableQuery();

        return dataModels(tableInfo, columns, modelQuery);
    }

    @Override
    public List<DataModel> getModels(String tableInfo, int position) {
        String columnTable = BIG_QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(position));
        columnTable = columnTable.replaceFirst(ITEM_REGEX, COLUMNS_TABLE);
        List<String> columns = JsonPath.read(tableInfo, columnTable);

        String modelQuery = createColumnTableQuery(position);

        return dataModels(tableInfo, columns, modelQuery);
    }

    private List<DataModel> dataModels(String tableInfo, List<String> columns, String modelQuery) {
        List<String> names = new ArrayList<>();
        List<String> types = new ArrayList<>();
        List<Boolean> primaries = new ArrayList<>();

        String query;

        for (int i = 0; i < columns.size(); i++) {
            query = modelQuery.replaceFirst(ITERATOR_REGEX, String.valueOf(i));
            names.add(getName(tableInfo, query));
            types.add(getType(tableInfo, query));
            primaries.add(getPrimary(tableInfo, query));
        }

        dataModelCreator.setDataModel(names, types, primaries);

        return dataModelCreator.getDataModel();
    }

    private String createColumnTableQuery() {
        return COLUMN_TABLE_QUERY;
    }

    private String createColumnTableQuery(int position) {
        return BIG_COLUMN_TABLE_QUERY.replaceFirst(ITERATOR_REGEX, String.valueOf(position));
    }

    private String getName(String tableInfo, String modelQuery) {
        return JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, NAME));
    }

    private String getType(String tableInfo, String modelQuery) {
        return JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, TYPE));
    }

    private boolean getPrimary(String tableInfo, String modelQuery) {

        return JsonPath.read(tableInfo, modelQuery.replaceFirst(KEY_REGEX, PRIMARY));
    }
}
