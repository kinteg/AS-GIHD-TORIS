package ru.iac.ASGIHDTORIS.common.model.table.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreator;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelCreatorFromDb;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class TableModelCreatorFromDbImpl implements TableModelCreatorFromDb {

    private final PatternTableRepo patternTableRepo;
    private final PatternRepo patternRepo;
    private final ColumnExporterRepo columnExporterRepo;
    private final TableModelCreator tableModelCreator;

    public TableModelCreatorFromDbImpl(
            PatternTableRepo patternTableRepo,
            PatternRepo patternRepo,
            ColumnExporterRepo columnExporterRepo,
            TableModelCreator tableModelCreator
    ) {

        this.patternTableRepo = patternTableRepo;
        this.patternRepo = patternRepo;
        this.columnExporterRepo = columnExporterRepo;
        this.tableModelCreator = tableModelCreator;
    }

    @Override
    public List<TableModel> createTableModels(long patternId) {
        Pattern pattern = patternRepo.findById(patternId);
        List<PatternTable> patternTables;

        if (pattern != null) {
            patternTables = patternTableRepo.findAllByPatternIdAndIsActive(pattern.getId(), true);
        } else {
            patternTables = Collections.emptyList();
        }

        List<List<DataModel>> modelList = new ArrayList<>();
        List<String> tableNames = new ArrayList<>();
        List<String> fileNames = new ArrayList<>();

        for (PatternTable patternTable :
                patternTables) {

            modelList.add(columnExporterRepo.exportDataModel(patternTable.getNameTable()));
            tableNames.add(patternTable.getNameTable());
            fileNames.add(patternTable.getNameFile());
        }

        try {
            columnExporterRepo.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        tableModelCreator.setTableModel(fileNames, tableNames, modelList);

        return tableModelCreator.getTableModel();
    }

}
