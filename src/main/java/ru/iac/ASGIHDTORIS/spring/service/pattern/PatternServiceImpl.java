package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternDateModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

import java.time.LocalDateTime;

@Service
public class PatternServiceImpl implements PatternService {

    private final PatternRepo patternRepo;

    public PatternServiceImpl(PatternRepo patternRepo) {
        this.patternRepo = patternRepo;
    }

    @Override
    public Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            Pattern pattern,
            PatternDateModel patternDateModel,
            String key,
            String sort
    ) {

        pageable = getPageable(pageable, key, sort);

        return getAll(pageable, pattern, patternDateModel);
    }

    private Pageable getPageable(Pageable pageable, String key, String sort) {
        if (sort.equalsIgnoreCase("desc")) {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(key).descending()
            );
        } else {
            return PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by(key).ascending()
            );
        }
    }

    private Page<Pattern> getAll(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {

        pattern = fixPattern(pattern);
        patternDateModel = fixPatternDataModel(patternDateModel);

        if (pattern.getIsArchive() == null && pattern.getSourceId() == null) {
            return getWithoutArchiveAndSource(pageable, pattern, patternDateModel);

        } else if (pattern.getIsArchive() != null && pattern.getSourceId() == null) {
            return getWithArchive(pageable, pattern, patternDateModel);

        } else if (pattern.getSourceId() != null && pattern.getIsArchive() == null) {
            return getWithSource(pageable, pattern, patternDateModel);

        } else {
            return getWithArchiveAndSource(pageable, pattern, patternDateModel);
        }

    }

    private Pattern fixPattern(Pattern pattern) {
        if (pattern.getName() == null) {
            pattern.setName("");
        }
        if (pattern.getDescription() == null) {
            pattern.setDescription("");
        }
        if (pattern.getDirection() == null) {
            pattern.setDirection("");
        }
        if (pattern.getManagement() == null) {
            pattern.setManagement("");
        }

        return pattern;
    }

    private PatternDateModel fixPatternDataModel(PatternDateModel patternDateModel) {

        if (patternDateModel.getDateCreation1() == null) {
            patternDateModel.setDateCreation1(LocalDateTime.MIN);
        }
        if (patternDateModel.getDateCreation2() == null) {
            patternDateModel.setDateCreation2(LocalDateTime.MAX);
        }
        if (patternDateModel.getDateDeactivation1() == null) {
            patternDateModel.setDateDeactivation1(LocalDateTime.MIN);
        }
        if (patternDateModel.getDateDeactivation2() == null) {
            patternDateModel.setDateDeactivation2(LocalDateTime.MAX);
        }
        if (patternDateModel.getDateActivation1() == null) {
            patternDateModel.setDateActivation1(LocalDateTime.MIN);
        }
        if (patternDateModel.getDateActivation2() == null) {
            patternDateModel.setDateActivation2(LocalDateTime.MAX);
        }
        if (patternDateModel.getLastUpdate1() == null) {
            patternDateModel.setLastUpdate1(LocalDateTime.MIN);
        }
        if (patternDateModel.getLastUpdate2() == null) {
            patternDateModel.setLastUpdate2(LocalDateTime.MAX);
        }
        if (patternDateModel.getFileCount1() == null) {
            patternDateModel.setFileCount1(Integer.MIN_VALUE);
        }
        if (patternDateModel.getFileCount2() == null) {
            patternDateModel.setFileCount2(Integer.MAX_VALUE);
        }

        return patternDateModel;
    }

    private Page<Pattern> getWithArchive(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {

        return patternRepo.findAllPatternByQuery(
                pageable,
                pattern.getName(),
                pattern.getDescription(),
                pattern.getDirection(),
                pattern.getManagement(),
                patternDateModel.getFileCount1(),
                patternDateModel.getFileCount2(),
                patternDateModel.getDateCreation1(),
                patternDateModel.getDateCreation2(),
                patternDateModel.getDateDeactivation1(),
                patternDateModel.getDateDeactivation2(),
                patternDateModel.getDateActivation1(),
                patternDateModel.getDateActivation2(),
                patternDateModel.getLastUpdate1(),
                patternDateModel.getLastUpdate2(),
                pattern.getIsArchive()
        );

    }

    private Page<Pattern> getWithoutArchiveAndSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {

        return patternRepo.findAllPatternByQuery(
                pageable,
                pattern.getName(),
                pattern.getDescription(),
                pattern.getDirection(),
                pattern.getManagement(),
                patternDateModel.getFileCount1(),
                patternDateModel.getFileCount2(),
                patternDateModel.getDateCreation1(),
                patternDateModel.getDateCreation2(),
                patternDateModel.getDateDeactivation1(),
                patternDateModel.getDateDeactivation2(),
                patternDateModel.getDateActivation1(),
                patternDateModel.getDateActivation2(),
                patternDateModel.getLastUpdate1(),
                patternDateModel.getLastUpdate2()
        );

    }

    private Page<Pattern> getWithArchiveAndSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {

        return patternRepo.findAllPatternByQuery(
                pageable,
                pattern.getName(),
                pattern.getDescription(),
                pattern.getDirection(),
                pattern.getManagement(),
                patternDateModel.getFileCount1(),
                patternDateModel.getFileCount2(),
                patternDateModel.getDateCreation1(),
                patternDateModel.getDateCreation2(),
                patternDateModel.getDateDeactivation1(),
                patternDateModel.getDateDeactivation2(),
                patternDateModel.getDateActivation1(),
                patternDateModel.getDateActivation2(),
                patternDateModel.getLastUpdate1(),
                patternDateModel.getLastUpdate2(),
                pattern.getIsArchive(),
                pattern.getSourceId()
        );

    }

    private Page<Pattern> getWithSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {

        return patternRepo.findAllPatternByQuery(
                pageable,
                pattern.getName(),
                pattern.getDescription(),
                pattern.getDirection(),
                pattern.getManagement(),
                patternDateModel.getFileCount1(),
                patternDateModel.getFileCount2(),
                patternDateModel.getDateCreation1(),
                patternDateModel.getDateCreation2(),
                patternDateModel.getDateDeactivation1(),
                patternDateModel.getDateDeactivation2(),
                patternDateModel.getDateActivation1(),
                patternDateModel.getDateActivation2(),
                patternDateModel.getLastUpdate1(),
                patternDateModel.getLastUpdate2(),
                pattern.getSourceId()
        );

    }




}
