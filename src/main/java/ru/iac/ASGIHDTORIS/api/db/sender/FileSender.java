package ru.iac.ASGIHDTORIS.api.db.sender;

import lombok.Data;
import ru.iac.ASGIHDTORIS.api.db.DataModel;
import ru.iac.ASGIHDTORIS.api.db.creator.Creator;
import ru.iac.ASGIHDTORIS.api.db.creator.DbPostgreSQLCreator;
import ru.iac.ASGIHDTORIS.api.db.loader.Loader;
import ru.iac.ASGIHDTORIS.api.factory.loader.LoaderFactory;

import java.io.File;
import java.sql.Connection;
import java.util.List;

@Data
public class FileSender implements DataSender {

    private File file;
    private Connection connection;

    public FileSender(File file, Connection connection) {
        this.file = file;
        this.connection = connection;
    }

    @Override
    public boolean send(List<DataModel> models, String nameTable) {

        if (createTable(models, nameTable)) {
            return sendData(models, nameTable);
        }

        return false;
    }

    private boolean createTable(List<DataModel> models, String nameTable) {
        Creator creator = new DbPostgreSQLCreator(connection);

        return creator.createTable(nameTable, models);
    }

    private boolean sendData(List<DataModel> models, String nameTable) {
        Loader loader = LoaderFactory.getParser(file.getName(), connection);

        return loader.insert(file, nameTable, models);
    }

}
