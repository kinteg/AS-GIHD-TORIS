package ru.iac.ASGIHDTORIS.spring.service.sender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.db.exporter.column.ColumnExporter;
import ru.iac.ASGIHDTORIS.db.sender.Sender;

import java.io.File;
import java.util.List;

@Service
public class FileSenderServiceImpl implements FileSenderService {

    private final ColumnExporter columnCreator;
    private final Sender sender;

    public FileSenderServiceImpl(@Qualifier("postgreSqlColumnExporter") ColumnExporter columnCreator, Sender sender) {
        this.columnCreator = columnCreator;
        this.sender = sender;
    }

    @Override
    public boolean sendFile(String name, File file) {
        List<DataModel> dataModels = columnCreator.exportDataModel(name);

        TableModel tableModel = TableModel
                .builder()
                .models(dataModels)
                .tableName(name)
                .build();

        return sender.insert(file, tableModel);
    }
}
