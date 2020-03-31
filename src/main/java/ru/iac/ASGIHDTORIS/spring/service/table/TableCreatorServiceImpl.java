package ru.iac.ASGIHDTORIS.spring.service.table;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.db.creator.Creator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;

import java.time.LocalDateTime;

@Slf4j
@Service
public class TableCreatorServiceImpl implements TableCreatorService {

    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;
    private final Creator creator;

    public TableCreatorServiceImpl(PatternRepo patternRepo, PatternTableRepo patternTableRepo, Creator creator) {
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
        this.creator = creator;
    }

    @Override
    public boolean addTable(TableModel tableModel, long id) {

        return checkPattern(id)
                && createTable(tableModel)
                && createPatternTable(tableModel, id);

    }

    private boolean checkPattern(long id) {
        return patternRepo.existsById(id);
    }

    private boolean createTable(TableModel tableModel) {
        boolean result = creator.createTable(tableModel);

        try {
            creator.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return result;
    }

        private boolean createPatternTable (TableModel tableModel,long id){
            PatternTable patternTable = new PatternTable()
                    .toBuilder()
                    .nameFile(tableModel.getFilename())
                    .nameTable(tableModel.getTableName())
                    .patternId(id)
                    .isArchive(false)
                    .dateActivation(LocalDateTime.now())
                    .dateCreation(LocalDateTime.now())
                    .lastUpdate(LocalDateTime.now())
                    .build();

            patternTableRepo.save(patternTable);
            Pattern pattern = patternRepo.findById(id);

            if (pattern.getFileCount() == null) {
                pattern.setFileCount(1);
            } else {
                pattern.setFileCount(pattern.getFileCount() + 1);
            }
            patternRepo.save(pattern);

            return true;
        }

    }
