package ru.iac.ASGIHDTORIS.lib.lib.parser.file.parser;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

import java.io.File;
import java.util.List;

@Component
public interface SimpleFileParser {

    List<TableModel> getFullTable(List<File> files);

    TableModel getFullTable(File file);


}
