package ru.iac.ASGIHDTORIS.spring.service.table;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.Status;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModelStatus;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.CreatorRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

@Slf4j
@Service
public class TableCreatorServiceImpl implements TableCreatorService {

    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final CreatorRepo creatorRepo;
    private final DataSource dataSource;

    public TableCreatorServiceImpl(PatternRepo patternRepo, PatternTableRepo patternTableRepo, CreatorRepo creatorRepo, DataSource dataSource) {
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.creatorRepo = creatorRepo;
        this.dataSource = dataSource;
    }

    @Override
    public PatternTableModelStatus addTable(TableModel tableModel, long id) {
        TableModelStatus tableModelStatus;
        PatternTable patternTable;

        if (checkPattern(id)) {
            tableModelStatus = createTable(tableModel);

            if (tableModelStatus.getStatus().equals(Status.OK)) {
                patternTable = createPatternTable(tableModel, id);
            } else {
                patternTable = PatternTable.builder().id(Long.parseLong("-1")).build();
            }
        } else {
            tableModelStatus = TableModelStatus.emptyTableModelStatus();
            patternTable = PatternTable.builder().id(Long.parseLong("-1")).build();
        }

        return PatternTableModelStatus
                .builder()
                .patternTable(patternTable)
                .tableModel(tableModelStatus)
                .build();

    }

    @Override
    public PatternTableModelStatus updateTable(TableModel tableModel, PatternTable patternTable) {
        tableModel.setTableName(patternTable.getNameTable());
        patternTable = updatePatternTable(tableModel, patternTable);
        TableModelStatus tableModelStatus = createTable(tableModel);

        return PatternTableModelStatus
                .builder()
                .patternTable(patternTable)
                .tableModel(tableModelStatus)
                .build();
    }

    private boolean checkPattern(long id) {
        return patternRepo.existsById(id);
    }

    private TableModelStatus createTable(TableModel tableModel) {
        return creatorRepo.createTable(tableModel);
    }

    private PatternTable createPatternTable(TableModel tableModel, long id) {
        Pattern pattern = patternRepo.findById(id);

        PatternTable patternTable = new PatternTable()
                .toBuilder()
                .nameFile(tableModel.getFilename())
                .nameTable(tableModel.getTableName())
                .patternId(id)
                .isArchive(false)
                .dateActivation(LocalDateTime.now())
                .dateCreation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .version(Long.valueOf(1))
                .isActive(true)
                .sourceId(pattern.getSourceId())
                .build();


        if (pattern.getFileCount() == null || pattern.getFileCount() == 0) {
            pattern.setFileCount(1);
        } else {
            pattern.setFileCount(pattern.getFileCount() + 1);
        }

        patternRepo.save(pattern);

        return patternTableRepo.save(patternTable);
    }

    private PatternTable updatePatternTable(TableModel tableModel, PatternTable patternTable) {

        PatternTable newPatternTable = new PatternTable(patternTable);



        return patternTableRepo.save(patternTable);
    }

    private void killOldPatternTable(PatternTable patternTable) {
        patternTable.setIsActive(false);
        patternTable.setIsArchive(true);

        patternTable.setDateDeactivation(LocalDateTime.now());
        patternTable.setDateKill(LocalDateTime.now());

        String oldName = patternTable.getNameTable();
        patternTable.setNameTable(patternTable.getNameTable() + "_v" + patternTable.getVersion());
        String newName = patternTable.getNameTable();

        killOldTable(oldName, newName);

        patternTableRepo.save(patternTable);
    }

    private void killOldTable(String oldName, String newName) {
        try (Statement stmt = dataSource.getConnection().createStatement()){
            stmt.execute("ALTER TABLE " + oldName + " RENAME TO " + newName + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
