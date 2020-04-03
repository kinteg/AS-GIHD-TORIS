package ru.iac.ASGIHDTORIS.spring.service.sender;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.table.TableModel;
import ru.iac.ASGIHDTORIS.spring.repo.ColumnExporterRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SenderRepo;

import java.io.File;
import java.util.List;

@Service
public class FileSenderServiceImpl implements FileSenderService {

    private final ColumnExporterRepo columnCreator;
    private final SenderRepo senderRepo;

    public FileSenderServiceImpl(@Qualifier("postgreSqlColumnExporterRepo") ColumnExporterRepo columnCreator, SenderRepo senderRepo) {
        this.columnCreator = columnCreator;
        this.senderRepo = senderRepo;
    }

    @Override
    public boolean sendFile(String name, File file) {
        List<DataModel> dataModels = columnCreator.exportDataModel(name);

        TableModel tableModel = TableModel
                .builder()
                .models(dataModels)
                .tableName(name)
                .build();

        return senderRepo.insert(file, tableModel);
    }
}
