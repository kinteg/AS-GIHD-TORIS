package ru.iac.ASGIHDTORIS.spring.service.dataSender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.lib.app.impl.DataSenderImpl;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;
import ru.iac.ASGIHDTORIS.spring.repo.PatternFileRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableFileRepo;
import ru.iac.ASGIHDTORIS.spring.repo.PatternTableRepo;
import ru.iac.ASGIHDTORIS.spring.service.file.FileService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DataSenderServiceImpl implements DataSenderService {

    @Value("${upload.path.pattern}")
    private String uploadPathPattern;
    @Value("${upload.path.pattern.table}")
    private String uploadPathPatternTable;

    private final FileService fileService;
    private final PatternTableRepo patternTableRepo;
    private final PatternRepo patternRepo;
    private final PatternFileRepo patternFileRepo;
    private final PatternTableFileRepo patternTableFileRepo;
    private final DataSenderImpl dataSender;

    public DataSenderServiceImpl(
            FileService fileService,
            PatternTableRepo patternTableRepo,
            PatternRepo patternRepo,
            PatternFileRepo patternFileRepo,
            PatternTableFileRepo patternTableFileRepo, DataSenderImpl dataSender) {

        this.fileService = fileService;
        this.patternTableRepo = patternTableRepo;
        this.patternRepo = patternRepo;
        this.patternFileRepo = patternFileRepo;
        this.patternTableFileRepo = patternTableFileRepo;
        this.dataSender = dataSender;
    }

    @Override
    public boolean sendData(MultipartFile multipartFile, Long id) throws IOException {
        if (
                        id < 0
                        || !patternTableRepo.existsById(id)
                        || patternTableRepo.findById((long) id).getIsArchive()
                        || !patternTableRepo.findById((long) id).getIsActive()
        ) {
            return false;
        } else {
            PatternTable patternTable = patternTableRepo.findById((long) id);
            File file = fileService.convertFile(multipartFile);

            boolean result = dataSender.sendFile(file, patternTable.getNameFile(), patternTable.getNameTable());

            uploadPatternTableFiles(multipartFile, id);
            cleanPatternTableFiles(id);

            fileService.delete(file);
            return result;
        }

    }

    @Override
    public boolean sendDates(MultipartFile multipartFile, Long id) throws IOException {
        if (
                        id < 0
                        || !patternRepo.existsById(id)
        ) {

            return false;
        } else {
            List<PatternTable> patternTables = patternTableRepo.findAllByPatternIdAndIsArchiveAndIsActive(id, false, true);
            File file = fileService.convertFile(multipartFile);
            boolean result = dataSender.sendFiles(file, createMap(patternTables));

            uploadPatternFiles(multipartFile, id);
            cleanPatternFiles(id);

            fileService.delete(file);
            return result;
        }

    }

    private Map<String, String> createMap(List<PatternTable> patternTables) {
        return patternTables.stream().collect(Collectors.toMap(PatternTable::getNameFile, PatternTable::getNameTable));
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

}
