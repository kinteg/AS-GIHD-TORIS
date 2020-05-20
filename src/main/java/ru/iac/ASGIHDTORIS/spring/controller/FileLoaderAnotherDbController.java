package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.lib.app.CommonFileParser;
import ru.iac.ASGIHDTORIS.lib.app.TableNameExporter;
import ru.iac.ASGIHDTORIS.lib.app.TestConnection;
import ru.iac.ASGIHDTORIS.lib.app.impl.TableNameExporterImpl;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.CustomConnection;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.FullTableModel;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.TableExporter;
import ru.iac.ASGIHDTORIS.lib.lib.db.exporter.impl.TableExporterImpl;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.fileExecutor.FileExecutor;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RequestMapping("api/fileLoaderAnotherDb")
@RestController
public class FileLoaderAnotherDbController {

    private final String DEFAULT_LIMIT = "5";

    private final TestConnection testConnection;
    private final FileService fileService;
    private final CommonFileParser commonFileParser;
    private final FileExecutor fileExecutor;

    public FileLoaderAnotherDbController(
            TestConnection testConnection,
            FileService fileService,
            CommonFileParser commonFileParser,
            FileExecutor fileExecutor) {
        this.testConnection = testConnection;
        this.commonFileParser = commonFileParser;
        this.fileService = fileService;
        this.fileExecutor = fileExecutor;
    }

    @PostMapping("/isConnection")
    public boolean testConnection(@ModelAttribute CustomConnection connection) {
        return testConnection.testConnection(connection);
    }

    @PostMapping("/getAllTableNames")
    public List<String> getAllTableNames(@ModelAttribute CustomConnection customConnection)
            throws SQLException {
        Connection connection = testConnection.getConnection(customConnection);
        TableNameExporter tableNameExporter = new TableNameExporterImpl(connection);

        List<String> names = tableNameExporter.getTableNames();

        connection.close();

        return names;
    }

    @PostMapping("/getAllFileNames")
    public List<String> getAllFileNames(@RequestParam(value = "file") MultipartFile multipartFile) {
        File file = fileService.convertFile(multipartFile);
        List<String> filenames = commonFileParser.getFileNames(file);
        fileService.delete(file);

        return filenames;
    }

    @PostMapping("/executeFile")
    public void executeFile(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @ModelAttribute CustomConnection customConnection,
            @ModelAttribute List<String> filenames,
            @ModelAttribute List<String> tableNames
    ) throws SQLException {
        File file = fileService.convertFile(multipartFile);
        Connection connection = testConnection.getConnection(customConnection);

        fileExecutor.executeFiles(file, connection, filenames, tableNames);
        connection.close();
    }

    @PostMapping("/getFileInfo")
    public FullTableModel getFileInfo(
            @RequestParam(value = "file") MultipartFile multipartFile,
            String filename,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Long limit
    ) {
        File file = fileService.convertFile(multipartFile);

        return commonFileParser.parseFile(file, limit, filename);
    }

    @PostMapping("/getTableInfo")
    public FullTableModel getTableInfo(
            String tableName,
            @ModelAttribute CustomConnection customConnection,
            @RequestParam(value = "limit", required = false, defaultValue = DEFAULT_LIMIT)
                    Integer limit
    ) throws SQLException {
        Connection connection = testConnection.getConnection(customConnection);
        TableExporter tableExporter = new TableExporterImpl(connection);

        FullTableModel fullTableModel = tableExporter.tableExporter(tableName, limit);

        connection.close();

        return fullTableModel;
    }

}
