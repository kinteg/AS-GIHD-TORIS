package ru.iac.ASGIHDTORIS.spring.service.export;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

@Service
public interface ExportDataFromDbService {

    FullTableModelPage getFullTableModel(PatternTable patternTable, Pageable pageable, String nameColumn, String sort);

}
