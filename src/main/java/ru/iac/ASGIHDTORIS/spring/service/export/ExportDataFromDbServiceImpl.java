package ru.iac.ASGIHDTORIS.spring.service.export;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.db.exporter.data.DataExporter;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.List;

@Service
public class ExportDataFromDbServiceImpl implements ExportDataFromDbService {

    private final ColumnExporter columnExporter;
    private final DataExporter dataExporter;

    public ExportDataFromDbServiceImpl(ColumnExporter columnExporter, DataExporter dataExporter) {
        this.columnExporter = columnExporter;
        this.dataExporter = dataExporter;
    }

    @Override
    public FullTableModelPage getFullTableModel(PatternTable patternTable, Pageable pageable, String nameColumn, String sort) {
        return createTableModel(patternTable, pageable, nameColumn, sort);
    }

    private FullTableModelPage createTableModel(PatternTable patternTable, Pageable pageable, String nameColumn, String sort) {
        TableModel tableModel = createTableModel(patternTable);

        if (nameColumn == null || nameColumn.isEmpty()) {
            return createTableModel(tableModel, pageable);
        } else {
            return createTableModel(tableModel, pageable, nameColumn, sort);
        }

    }

    private TableModel createTableModel(PatternTable patternTable) {
        List<DataModel> dataModels = columnExporter.exportDataModel(patternTable.getNameTable());

        return TableModel
                .builder()
                .filename(patternTable.getNameFile())
                .tableName(patternTable.getNameTable())
                .models(dataModels)
                .build();
    }

    private FullTableModelPage createTableModel(TableModel tableModel, Pageable pageable, String nameColumn, String sort) {
        return dataExporter.exportData(tableModel, pageable, nameColumn, sort);
    }

    private FullTableModelPage createTableModel(TableModel tableModel, Pageable pageable) {
        return dataExporter.exportData(tableModel, pageable);
    }

}
