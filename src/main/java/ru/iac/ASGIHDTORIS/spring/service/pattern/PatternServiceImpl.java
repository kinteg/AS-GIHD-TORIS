package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.model.domain.PatternDateModel;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

@Service
public class PatternServiceImpl implements PatternService {

    private final PatternRepo patternRepo;

    public PatternServiceImpl(PatternRepo patternRepo) {
        this.patternRepo = patternRepo;
    }

    @Override
    public Page<Pattern> findAllPatternByQuery(
            Pageable pageable,
            PatternDateModel pattern
    ) {

//        pageable = getPageable(pageable, pattern.getKey(), pattern.getSort());

//        return getAll(pageable, pattern);
        return null;
    }

//    private Pageable getPageable(Pageable pageable, String key, String sort) {
//        if (key == null || key.isEmpty()) {
//            key = "id";
//        }
//
//        if (sort == null || sort.isEmpty()) {
//            sort = "asc";
//        }
//
//        if (sort.equalsIgnoreCase("desc")) {
//            return PageRequest.of(
//                    pageable.getPageNumber(),
//                    pageable.getPageSize(),
//                    Sort.by(key).descending()
//            );
//        } else {
//            return PageRequest.of(
//                    pageable.getPageNumber(),
//                    pageable.getPageSize(),
//                    Sort.by(key).ascending()
//            );
//        }
//    }
//
//    private Page<Pattern> getAll(Pageable pageable, PatternDateModel pattern) {
//
//        fixPattern(pattern);
//        fixPatternDataModel(pattern);
//
//        if (pattern.getIsArchive() == null && pattern.getSourceId() == null) {
//            return getWithoutArchiveAndSource(pageable, pattern);
//
//        } else if (pattern.getIsArchive() != null && pattern.getSourceId() == null) {
//            return getWithArchive(pageable, pattern);
//
//        } else if (pattern.getSourceId() != null && pattern.getIsArchive() == null) {
//            return getWithSource(pageable, pattern);
//
//        } else {
//            return getWithArchiveAndSource(pageable, pattern);
//        }
//
//    }
//
//    private void fixPattern(PatternDateModel pattern) {
//        if (pattern.getName() == null) {
//            pattern.setName("");
//        }
//        if (pattern.getDescription() == null) {
//            pattern.setDescription("");
//        }
//        if (pattern.getDirection() == null) {
//            pattern.setDirection("");
//        }
//        if (pattern.getManagement() == null) {
//            pattern.setManagement("");
//        }
//        if (pattern.getFileCount1() == null) {
//            pattern.setFileCount1(Integer.MIN_VALUE);
//        }
//        if (pattern.getFileCount2() == null) {
//            pattern.setFileCount2(Integer.MAX_VALUE);
//        }
//
//    }
//
//    private void fixPatternDataModel(PatternDateModel pattern) {
//
//        if (pattern.getDateCreation1() == null) {
//            pattern.setDateCreation1(LocalDate.of(1970, 1, 1));
//        }
//        if (pattern.getDateCreation2() == null) {
//            pattern.setDateCreation2(LocalDate.of(3000, 1, 1));
//        }
//        if (pattern.getDateActivation1() == null) {
//            pattern.setDateActivation1(LocalDate.of(1970, 1, 1));
//        }
//        if (pattern.getDateActivation2() == null) {
//            pattern.setDateActivation2(LocalDate.of(3000, 1, 1));
//        }
//        if (pattern.getLastUpdate1() == null) {
//            pattern.setLastUpdate1(LocalDate.of(1970, 1, 1));
//        }
//        if (pattern.getLastUpdate2() == null) {
//            pattern.setLastUpdate2(LocalDate.of(3000, 1, 1));
//        }
//
//    }
//
//    private Page<Pattern> getWithArchive(Pageable pageable, PatternDateModel pattern) {
//
//        return patternRepo.findAllPatternByQuery(
//                pageable,
//                pattern.getName(),
//                pattern.getDescription(),
//                pattern.getDirection(),
//                pattern.getManagement(),
//                pattern.getFileCount1(),
//                pattern.getFileCount2(),
//                pattern.getDateCreation1().atTime(0, 0, 0),
//                pattern.getDateCreation2().atTime(23, 59, 59),
//                pattern.getDateDeactivation1().atTime(0, 0, 0),
//                pattern.getDateDeactivation2().atTime(23, 59, 59),
//                pattern.getDateActivation1().atTime(0, 0, 0),
//                pattern.getDateActivation2().atTime(23, 59, 59),
//                pattern.getLastUpdate1().atTime(0, 0, 0),
//                pattern.getLastUpdate2().atTime(23, 59, 59),
//                pattern.getIsArchive()
//        );
//
//    }
//
//    private Page<Pattern> getWithoutArchiveAndSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {
//
//        return patternRepo.findAllPatternByQuery(
//                pageable,
//                pattern.getName(),
//                pattern.getDescription(),
//                pattern.getDirection(),
//                pattern.getManagement(),
//                patternDateModel.getFileCount1(),
//                patternDateModel.getFileCount2(),
//                patternDateModel.getDateCreation1().atTime(0, 0, 0),
//                patternDateModel.getDateCreation2().atTime(23, 59, 59),
//                patternDateModel.getDateDeactivation1().atTime(0, 0, 0),
//                patternDateModel.getDateDeactivation2().atTime(23, 59, 59),
//                patternDateModel.getDateActivation1().atTime(0, 0, 0),
//                patternDateModel.getDateActivation2().atTime(23, 59, 59),
//                patternDateModel.getLastUpdate1().atTime(0, 0, 0),
//                patternDateModel.getLastUpdate2().atTime(23, 59, 59)
//        );
//
//    }
//
//    private Page<Pattern> getWithArchiveAndSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {
//
//        return patternRepo.findAllPatternByQuery(
//                pageable,
//                pattern.getName(),
//                pattern.getDescription(),
//                pattern.getDirection(),
//                pattern.getManagement(),
//                patternDateModel.getFileCount1(),
//                patternDateModel.getFileCount2(),
//                patternDateModel.getDateCreation1().atTime(0, 0, 0),
//                patternDateModel.getDateCreation2().atTime(23, 59, 59),
//                patternDateModel.getDateDeactivation1().atTime(0, 0, 0),
//                patternDateModel.getDateDeactivation2().atTime(23, 59, 59),
//                patternDateModel.getDateActivation1().atTime(0, 0, 0),
//                patternDateModel.getDateActivation2().atTime(23, 59, 59),
//                patternDateModel.getLastUpdate1().atTime(0, 0, 0),
//                patternDateModel.getLastUpdate2().atTime(23, 59, 59),
//                pattern.getIsArchive(),
//                pattern.getSourceId()
//        );
//
//    }
//
//    private Page<Pattern> getWithSource(Pageable pageable, Pattern pattern, PatternDateModel patternDateModel) {
//
//        return patternRepo.findAllPatternByQuery(
//                pageable,
//                pattern.getName(),
//                pattern.getDescription(),
//                pattern.getDirection(),
//                pattern.getManagement(),
//                patternDateModel.getFileCount1(),
//                patternDateModel.getFileCount2(),
//                patternDateModel.getDateCreation1().atTime(0, 0, 0),
//                patternDateModel.getDateCreation2().atTime(23, 59, 59),
//                patternDateModel.getDateDeactivation1().atTime(0, 0, 0),
//                patternDateModel.getDateDeactivation2().atTime(23, 59, 59),
//                patternDateModel.getDateActivation1().atTime(0, 0, 0),
//                patternDateModel.getDateActivation2().atTime(23, 59, 59),
//                patternDateModel.getLastUpdate1().atTime(0, 0, 0),
//                patternDateModel.getLastUpdate2().atTime(23, 59, 59),
//                pattern.getSourceId()
//        );
//
//    }




}
