package ru.iac.ASGIHDTORIS.api.db.sender;

import lombok.Data;
import ru.iac.ASGIHDTORIS.api.db.loader.LoaderImpl;
import ru.iac.ASGIHDTORIS.api.db.model.data.DataModel;
import ru.iac.ASGIHDTORIS.api.db.creator.Creator;
import ru.iac.ASGIHDTORIS.api.db.creator.PostgreSqlCreator;
import ru.iac.ASGIHDTORIS.api.db.loader.Loader;
import ru.iac.ASGIHDTORIS.api.factory.loader.LoaderFactory;

import java.io.File;
import java.sql.Connection;
import java.util.List;

@Data
public class FileSender implements DataSender {

    private File file;
    private Connection connection;

    public FileSender(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean send(File file, List<DataModel> models, String nameTable) {
        boolean result = false;
        if (createTable(models, nameTable)) {
            result = sendData(file, models, nameTable);
        }
        file.delete();

        return result;
    }

    private boolean createTable(List<DataModel> models, String nameTable) {
        Creator creator = new PostgreSqlCreator(connection);

        return creator.createTable(nameTable, models);
    }

    private boolean sendData(File file, List<DataModel> models, String nameTable) {
        Loader loader = new LoaderImpl(connection);

        return loader.insert(file, nameTable, models);
    }

}
