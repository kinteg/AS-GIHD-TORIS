package ru.iac.ASGIHDTORIS.spring.service.table;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;

@Service
public interface TableCreatorService {

    PatternTableModelStatus addTable(TableModel tableModel, long id);

}
