package ru.iac.ASGIHDTORIS.spring.service.dataSender;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.Status;
import ru.iac.ASGIHDTORIS.common.factory.FileParserFactory;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.file.FileStatusModel;
import ru.iac.ASGIHDTORIS.parser.file.fixer.ColumnCreator;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;
import ru.iac.ASGIHDTORIS.spring.repo.*;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;
import ru.iac.ASGIHDTORIS.spring.service.sender.FileSenderService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DataSenderServiceImpl implements DataSenderService {

    @Value("${upload.path.pattern}")
    private String uploadPathPattern;
    @Value("${upload.path.pattern.table}")
    private String uploadPathPatternTable;

    private final FileService fileService;
    private final FileSenderService fileSenderService;
    private final PatternTableRepo patternTableRepo;
    private final PatternRepo patternRepo;
    private final PatternFileRepo patternFileRepo;
    private final PatternTableFileRepo patternTableFileRepo;
    private final ColumnExporterRepo columnExporterRepo;

    public DataSenderServiceImpl(
            FileService fileService,
            FileSenderService fileSenderService,
            PatternTableRepo patternTableRepo,
            PatternRepo patternRepo,
            PatternFileRepo patternFileRepo,
            PatternTableFileRepo patternTableFileRepo,
            ColumnExporterRepo columnExporterRepo) {
        this.fileService = fileService;
        this.fileSenderService = fileSenderService;
        this.patternTableRepo = patternTableRepo;
        this.patternRepo = patternRepo;
        this.patternFileRepo = patternFileRepo;
        this.patternTableFileRepo = patternTableFileRepo;
        this.columnExporterRepo = columnExporterRepo;
    }

    @Override
    public boolean sendData(MultipartFile multipartFile, Long id) throws IOException {
        if (
                multipartFile == null
                        || id == null
                        || id < 0
                        || !patternTableRepo.existsById(id)
                        || patternTableRepo.findById((long) id).getIsArchive()
                        || !patternTableRepo.findById((long) id).getIsActive()
        ) {
            return false;
        } else {
            PatternTable patternTable = patternTableRepo.findById((long) id);
            File file = fileService.convertFile(multipartFile);
            boolean result = fileSenderService.sendFile(patternTable, file);

            uploadPatternTableFiles(multipartFile, id);
            cleanPatternTableFiles(id);

            file.delete();
            return result;
        }

    }

    @Override
    public boolean sendDates(MultipartFile multipartFile, Long id) throws IOException {
        if (
                multipartFile == null
                        || id == null
                        || id < 0
                        || !patternRepo.existsById(id)
        ) {

            return false;
        } else {
            List<PatternTable> patternTables = patternTableRepo.findAllByPatternIdAndIsArchiveAndIsActive(id, false, true);
            File file = fileService.convertFile(multipartFile);

            uploadPatternFiles(multipartFile, id);
            cleanPatternFiles(id);

            return file != null && fileSenderService.sendFiles(patternTables, file);
        }

    }

    @Override
    public FileStatusModel checkData(MultipartFile multipartFile, Long id) throws Exception {
        if (
                multipartFile == null
                        || id == null
                        || id < 0
                        || !patternTableRepo.existsById(id)
                        || patternTableRepo.findById((long) id).getIsArchive()
                        || !patternTableRepo.findById((long) id).getIsActive()
        ) {
            return null;
        } else {
            File file = fileService.convertFile(multipartFile);
            PatternTable patternTable = patternTableRepo.findById((long) id);

            File targetFile = fileService.getFile(file, patternTable.getNameFile());
            int tableColumnSize = columnExporterRepo.exportDataModel(patternTable.getNameTable()).size();
            FileParser fileParser = FileParserFactory.getParser(FilenameUtils.getExtension(targetFile.getName()));
            Reader reader = fileParser.createReader(targetFile);
            int fileColumnSize = getNamesColumn(reader).size();
            FileStatusModel fileStatusModel;

            if (tableColumnSize == fileColumnSize) {
                fileStatusModel = FileStatusModel
                        .builder()
                        .filename(patternTable.getNameFile())
                        .tableName(patternTable.getNameTable())
                        .status(Status.OK)
                        .error("-")
                        .warn("-")
                        .build();
            } else if (tableColumnSize < fileColumnSize) {
                fileStatusModel = FileStatusModel
                        .builder()
                        .filename(patternTable.getNameFile())
                        .tableName(patternTable.getNameTable())
                        .status(Status.ERROR)
                        .error("В табличке меньше полей, чем в файле. Возможна утечка данных.")
                        .warn("-")
                        .build();
            } else {
                fileStatusModel = FileStatusModel
                        .builder()
                        .filename(patternTable.getNameFile())
                        .tableName(patternTable.getNameTable())
                        .status(Status.WARN)
                        .error("-")
                        .warn("В табличке больше полей, чем в файле.")
                        .build();
            }

            file.delete();
            log.info(fileStatusModel.getStatus());
            return fileStatusModel;
        }
    }

    @Override
    public List<FileStatusModel> checkDates(MultipartFile multipartFile, Long id) throws Exception {
        if (
                multipartFile == null
                        || id == null
                        || id < 0
                        || !patternRepo.existsById(id)
        ) {

            return null;
        } else {
            List<PatternTable> patternTables = patternTableRepo.findAllByPatternIdAndIsArchiveAndIsActive(id, false, true);
            List<FileStatusModel> fileStatusModels = new ArrayList<>();

            for (PatternTable patternTable :
                    patternTables) {
                fileStatusModels.add(checkData(multipartFile, patternTable.getId()));
            }

            return fileStatusModels;
        }
    }

    private void uploadPatternTableFiles(MultipartFile file, Long id) throws IOException {
        String fileName = uploadFile(file, uploadPathPatternTable);

        PatternTableFile patternTableFile = PatternTableFile
                .builder()
                .patternTableId(id)
                .file(fileName)
                .dateCreation(LocalDateTime.now())
                .build();
        patternTableFileRepo.save(patternTableFile);
    }

    private void uploadPatternFiles(MultipartFile file, Long id) throws IOException {
        String fileName = uploadFile(file, uploadPathPattern);

        PatternFile patternTableFile = PatternFile
                .builder()
                .patternId(id)
                .file(fileName)
                .dateCreation(LocalDateTime.now())
                .build();
        patternFileRepo.save(patternTableFile);
    }

    private String uploadFile(MultipartFile file, String uploadPath) throws IOException {
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        LocalDateTime dataCreation = LocalDateTime.now();
        String fileName = dataCreation.format(
                DateTimeFormatter.ISO_DATE_TIME) + "." +
                file.getOriginalFilename();
        String fullPath = uploadPath + "/" + fileName;

        file.transferTo(new File(fullPath));

        return fileName;
    }

    private boolean cleanPatternTableFiles(Long id) {
        if (patternTableFileRepo.countAllByPatternTableId(id) > 3) {
            PatternTableFile patternTableFile = patternTableFileRepo
                    .findByPatternTableIdAndDateCreationMin(id);

            File file = new File(
                    uploadPathPatternTable.trim() + "/" +
                            patternTableFile.getFile().trim());

            if (file.exists()) {
                file.delete();
            }

            patternTableFileRepo.delete(patternTableFile);
        }

        return patternTableFileRepo.countAllByPatternTableId(id) <= 3 || cleanPatternTableFiles(id);
    }

    private boolean cleanPatternFiles(Long id) {
        if (patternFileRepo.countAllByPatternId(id) > 3) {
            PatternFile patternFile = patternFileRepo
                    .findByPatternIdAndDateCreationMin(id);

            File file = new File(
                    uploadPathPattern.trim() + "/" +
                            patternFile.getFile().trim());

            if (file.exists()) {
                file.delete();
            }

            patternFileRepo.delete(patternFile);
        }

        return patternFileRepo.countAllByPatternId(id) <= 3 || cleanPatternFiles(id);
    }

    private List<DataModel> getNamesColumn(Reader reader) throws Exception {
        List<String> nameColumns = reader.readNext();
        return ColumnCreator.createColumns(nameColumns);
    }

}
