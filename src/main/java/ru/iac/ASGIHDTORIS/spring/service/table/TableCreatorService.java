package ru.iac.ASGIHDTORIS.spring.service.table;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

@Service
public interface TableCreatorService {

    PatternTableModelStatus addTable(TableModel tableModel, long id);

    PatternTableModelStatus updateTable(TableModel tableModel, PatternTable patternTable);
}
