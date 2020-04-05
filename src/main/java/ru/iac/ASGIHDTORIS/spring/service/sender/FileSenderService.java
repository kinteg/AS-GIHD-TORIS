package ru.iac.ASGIHDTORIS.spring.service.sender;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.io.File;
import java.util.List;

@Service
public interface FileSenderService {

    boolean sendFile(PatternTable patternTable, File file);

    boolean sendFiles(List<PatternTable> patternTables, File file);

}
