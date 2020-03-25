package ru.iac.ASGIHDTORIS.db.exporter.data;


import org.springframework.data.domain.Pageable;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;


public interface DataExporter extends AutoCloseable {

    FullTableModelPage exportData(TableModel tableModel, Pageable pageable) ;

    FullTableModelPage exportData(TableModel tableModel, Pageable pageable, String nameColumn) ;

}
