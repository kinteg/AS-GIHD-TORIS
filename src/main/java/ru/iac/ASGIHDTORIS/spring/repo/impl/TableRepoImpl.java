package ru.iac.ASGIHDTORIS.spring.repo.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.spring.component.CountMapper;
import ru.iac.ASGIHDTORIS.spring.component.FullRepoHelper;
import ru.iac.ASGIHDTORIS.spring.component.Mapper.Mapper;
import ru.iac.ASGIHDTORIS.spring.repo.TableRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TableRepoImpl implements TableRepo {

    private final String SELECT_QUERY = "SELECT * FROM ";
    private final String COUNT_QUERY = "SELECT count(*) FROM ";

    private final ColumnExporter columnExporter;
    private final Mapper<List<Map<String, String>>> mapper;
    private final CountMapper countMapper;
    private final FullRepoHelper<Map<String, String>> fullRepoHelper;


    public TableRepoImpl(ColumnExporter columnExporter, @Qualifier("tableMapper") Mapper<List<Map<String, String>>> mapper, CountMapper countMapper, FullRepoHelper<Map<String, String>> fullRepoHelper) {
        this.columnExporter = columnExporter;
        this.mapper = mapper;
        this.countMapper = countMapper;
        this.fullRepoHelper = fullRepoHelper;
    }

    @Override
    public FullTableModelPage getTable(String tableName, String fileName, SearchModel searchModel) {
        MapSqlParameterSource params = new MapSqlParameterSource();

        TableModel tableModel = TableModel
                .builder()
                .filename(fileName)
                .tableName(tableName)
                .models(columnExporter.exportDataModel(tableName))
                .build();

        mapper.setKeys(tableModel.getModels().stream().map(DataModel::getKey).collect(Collectors.toList()));

        String valueQuery = createQueryValue(searchModel, params);
        String pageQuery = fullRepoHelper.createPageQuery(searchModel.getPageable(), searchModel.getSort(), searchModel.getNameColumn(), tableModel.getModels().get(0).getKey());
        String selectQuery = SELECT_QUERY + tableName + valueQuery + pageQuery;
        String countQuery = COUNT_QUERY + tableName + valueQuery;

        List<Map<String, String>> values = fullRepoHelper.getAll(selectQuery, params, mapper);
        int count = fullRepoHelper.getCount(countQuery, params, countMapper);

        Page<Map<String, String>> pageValues = new PageImpl<>(values, searchModel.getPageable(), count);

        return FullTableModelPage
                .builder()
                .tableModel(tableModel)
                .values(pageValues)
                .build();
    }

    private String createQueryValue(SearchModel searchModel, MapSqlParameterSource params) {
        List<String> valuesQuery = new ArrayList<>();
        List<String> values = searchModel.getValues();
        List<String> keys = searchModel.getKeys();

        if (keys != null && values != null) {

            for (int i = 0; i < keys.size() && i < values.size(); i++) {

                valuesQuery.add(" cast(" +
                        keys.get(i).trim() +
                        " as text) ILIKE :" +
                        values.get(i).trim()
                );

                params.addValue(
                        keys.get(i).trim(),
                        "%" + values.get(i).trim() + "%"
                );
            }
        }

        return (valuesQuery.size() != 0 ? " WHERE " : "") +
                String.join(" AND ", valuesQuery);

    }


}
