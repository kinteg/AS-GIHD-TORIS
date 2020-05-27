package ru.iac.ASGIHDTORIS.spring.service.patterTable;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.User;

import java.util.List;

@Service
public interface PatternTableService {

    PatternTableModelStatus createPatternTable(TableModel tableModel, DataModelList dataModelList, Long patternId, User user);

    FullTableModelPage getTable(Long id, SearchModel searchModel, Pageable pageable);

    PatternTable archivePatternTable(Long id, User user);

    PatternTable deArchivePatternTable(Long id, User user);

    List<PatternTable> archivePatternTablesByPatternId(Long patternId, User user);

    List<PatternTable> deArchivePatternTablesByPatternId(Long patternId, User user);

    List<PatternTable> archivePatternTablesBySourceId(Long id, User user);

    List<PatternTable> deArchivePatternTablesBySourceId(Long id, User user);

    PatternTableModelStatus updatePatternTable(TableModel tableModel, DataModelList dataModelList, Long patternTableId, User user);
}
