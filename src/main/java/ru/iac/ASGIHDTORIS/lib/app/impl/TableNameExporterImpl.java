package ru.iac.ASGIHDTORIS.lib.app.impl;

import ru.iac.ASGIHDTORIS.lib.app.TableNameExporter;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.NameExporterRepo;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.impl.NameExporterRepoImpl;

import java.sql.Connection;
import java.util.List;

public class TableNameExporterImpl implements TableNameExporter {

    private final NameExporterRepo nameExporterRepo;

    public TableNameExporterImpl(Connection connection) {
        nameExporterRepo = new NameExporterRepoImpl(connection);
    }

    @Override
    public List<String> getTableNames() {
        return nameExporterRepo.exportNames();
    }

}
