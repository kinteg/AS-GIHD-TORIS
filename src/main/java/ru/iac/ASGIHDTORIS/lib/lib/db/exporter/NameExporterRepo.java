package ru.iac.ASGIHDTORIS.lib.lib.db.exporter;

import java.io.Closeable;
import java.util.List;

public interface NameExporterRepo extends Closeable {

    List<String> exportNames();

}
