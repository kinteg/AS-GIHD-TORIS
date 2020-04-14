package ru.iac.ASGIHDTORIS.spring.service.patterTable;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModelList;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.serch.SearchModel;
import ru.iac.ASGIHDTORIS.common.model.table.PatternTableModelStatus;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.List;

@Service
public interface PatternTableService {

    PatternTableModelStatus createPatternTable(TableModel tableModel, DataModelList dataModelList, Long patternId);

    FullTableModelPage getTable(Long id, SearchModel searchModel, Pageable pageable);

    PatternTable archivePatternTable(Long id);

    PatternTable deArchivePatternTable(Long id);

    List<PatternTable> archivePatternTablesByPatternId(Long patternId);

    List<PatternTable> deArchivePatternTablesByPatternId(Long patternId);

    List<PatternTable> archivePatternTablesBySourceId(Long id);

    List<PatternTable> deArchivePatternTablesBySourceId(Long id);

    PatternTableModelStatus updatePatternTable(TableModel tableModel, DataModelList dataModelList, Long patternTableId);
}
