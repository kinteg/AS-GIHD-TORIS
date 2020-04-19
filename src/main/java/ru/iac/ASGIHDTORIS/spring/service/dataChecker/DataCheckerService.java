package ru.iac.ASGIHDTORIS.spring.service.dataChecker;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;

import java.io.File;
import java.util.List;

@Service
public interface DataCheckerService {

    List<FileStatusModel> checkDates(File file, Long id) throws Exception;

    FileStatusModel checkData(File file, Long id) throws Exception;

}
