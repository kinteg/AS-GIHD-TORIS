package ru.iac.ASGIHDTORIS.service.sender;

import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.db.model.table.TableModel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface DbService {

    String sendData(MultipartFile multipartFile, List<TableModel> tableInfo, long sourceId) throws IOException, SQLException;

}
