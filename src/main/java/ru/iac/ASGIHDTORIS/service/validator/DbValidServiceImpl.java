package ru.iac.ASGIHDTORIS.service.validator;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.api.factory.archive.ArchiveFactory;
import ru.iac.ASGIHDTORIS.api.parser.archive.ArchiveParser;
import ru.iac.ASGIHDTORIS.api.parser.converter.FileConverter;
import ru.iac.ASGIHDTORIS.domain.Pattern;
import ru.iac.ASGIHDTORIS.domain.PatternTable;
import ru.iac.ASGIHDTORIS.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.repo.PatternTableRepo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DbValidServiceImpl implements DbValidService {

    private final PatternRepo patternRepo;
    private final PatternTableRepo patternTableRepo;

    public DbValidServiceImpl(PatternRepo patternRepo, PatternTableRepo patternTableRepo) {
        this.patternRepo = patternRepo;
        this.patternTableRepo = patternTableRepo;
    }

    public boolean isValid(MultipartFile multipartFile, long sourceId) throws IOException {
        File file = FileConverter.multipartIntoFile(multipartFile);
        Pattern pattern = patternRepo.findTopBySourceIdOrderByDateCreationDesc(sourceId);
        List<PatternTable> patternTables;
        List<File> files = getFiles(file);

        if (pattern != null) {
            patternTables = patternTableRepo.findByPatternId(pattern.getId());
        } else {
            patternTables = Collections.emptyList();
        }

        if (patternTables.size() != files.size()) {
            for (File value :
                    files) {
                value.delete();
            }
            file.delete();
            return false;
        }

        for (File value : files) {
            if (!patternTables.contains(new PatternTable(value.getName()))) {
                value.delete();
                file.delete();
                return false;
            }
            value.delete();
        }

        file.delete();
        return true;
    }

    private List<File> getFiles(File file) throws IOException {
        List<File> files = new ArrayList<>();

        if (ArchiveFactory.isArchive(file.getName())) {
            ArchiveParser archiveParser =  ArchiveFactory.getParser(file.getName());
            files = archiveParser.getFiles(file);
        }

        return files;

    }

}
