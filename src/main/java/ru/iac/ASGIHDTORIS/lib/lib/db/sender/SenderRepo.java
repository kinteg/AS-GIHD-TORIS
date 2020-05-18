package ru.iac.ASGIHDTORIS.lib.lib.db.sender;

import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

public interface SenderRepo extends AutoCloseable {

    boolean insert(Reader reader, TableModel tableModel);

}
