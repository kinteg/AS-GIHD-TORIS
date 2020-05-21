package ru.iac.ASGIHDTORIS.lib.app;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;

import java.io.File;
import java.util.List;

@Component
public interface CommonFileParser {

    List<FullTableModel> parseFile(File file);

    List<FullTableModel> parseFile(File file, long limit);

    FullTableModel parseFile(File file, long limit, String fileName);

    List<String> getFileNames(File file);

}
