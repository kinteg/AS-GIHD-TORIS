package ru.iac.ASGIHDTORIS.spring.service.pattern;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatternServiceImpl implements PatternService {

    private final PatternRepo patternRepo;
    private final LoggerSender<Pattern> patternLoggerSender;
    private final BeforeAfter<Pattern> patternBeforeAfter;
    private final Validator<Pattern> patternValidator;

    public PatternServiceImpl(
            PatternRepo patternRepo,
            @Qualifier("getPatternValidator") Validator<Pattern> patternValidator,
            @Qualifier("patternLoggerSender") LoggerSender<Pattern> patternLoggerSender,
            @Qualifier("patternBeforeAfter") BeforeAfter<Pattern> patternBeforeAfter) {
        this.patternRepo = patternRepo;
        this.patternLoggerSender = patternLoggerSender;
        this.patternBeforeAfter = patternBeforeAfter;
        this.patternValidator = patternValidator;
    }

    public Pattern createPattern(Pattern pattern) {

        pattern.setDateCreation(LocalDateTime.now());
        pattern.setDateActivation(LocalDateTime.now());
        pattern.setLastUpdate(LocalDateTime.now());
        pattern.setArchiveFileCount(0);
        pattern.setFileCount(0);
        pattern.setIsArchive(false);

        Pattern patternAfter = patternValidator.isValid(pattern)
                ? patternRepo.save(pattern)
                : Pattern.builder().id(Long.parseLong("-1")).build();


        long loggerId = patternLoggerSender.afterCreate(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterCreate(patternAfter, loggerId);
        }

        return patternAfter;
    }

    @Override
    public Pattern archivePattern(Long id) {
        Pattern patternBefore, patternAfter;
        if (id == null) {
            patternAfter = Pattern.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternRepo.existsById(id)) {
            patternAfter = Pattern.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternRepo.findById((long) id);
            patternBefore = Pattern
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateDeactivation(patternAfter.getDateDeactivation())
                    .build();

            patternAfter.setIsArchive(true);
            patternAfter.setDateDeactivation(LocalDateTime.now());

            patternRepo.save(patternAfter);

        }

        long loggerId = patternLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
    }

    @Override
    public Pattern deArchivePattern(Long id) {
        Pattern patternBefore, patternAfter;

        if (id == null) {
            patternAfter = Pattern.builder().id((long) -4).build();
            patternBefore = patternAfter;
        } else if (!patternRepo.existsById(id)) {
            patternAfter = Pattern.builder().id((long) -3).build();
            patternBefore = patternAfter;
        } else {
            patternAfter = patternRepo.findById((long) id);
            patternBefore = Pattern
                    .builder()
                    .isArchive(patternAfter.getIsArchive())
                    .dateActivation(patternAfter.getDateDeactivation())
                    .build();

            patternAfter.setIsArchive(false);
            patternAfter.setDateActivation(LocalDateTime.now());

            patternRepo.save(patternAfter);

        }

        long loggerId = patternLoggerSender.afterDeArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterDeArchive(patternBefore, patternAfter, loggerId);
        }

        return patternAfter;
    }

    @Override
    public List<Pattern> archivePatterns(Long sourceId) {
        List<Pattern> patternsBefore, patternsAfter;

        if (sourceId == null) {
            patternsAfter = Collections.singletonList(Pattern.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternRepo.existsBySourceId(sourceId)) {
            patternsAfter = Collections.singletonList(Pattern.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .map(v -> Pattern
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateDeactivation(v.getDateDeactivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(true);
                        v.setDateDeactivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternLoggerSender.afterArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternBeforeAfter.afterArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public List<Pattern> deArchivePatterns(Long sourceId) {
        List<Pattern> patternsBefore, patternsAfter;

        if (sourceId == null) {
            patternsAfter = Collections.singletonList(Pattern.builder().id((long) -4).build());
            patternsBefore = patternsAfter;
        } else if (!patternRepo.existsBySourceId(sourceId)) {
            patternsAfter = Collections.singletonList(Pattern.builder().id((long) -3).build());
            patternsBefore = patternsAfter;
        } else {
            patternsBefore = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .map(v -> Pattern
                            .builder()
                            .isArchive(v.getIsArchive())
                            .dateActivation(v.getDateActivation())
                            .build())
                    .collect(Collectors.toList());

            patternsAfter = patternRepo
                    .findAllBySourceId(sourceId)
                    .stream()
                    .peek(v -> {
                        v.setIsArchive(false);
                        v.setDateActivation(LocalDateTime.now());
                    })
                    .collect(Collectors.toList());

            patternsAfter = patternRepo.saveAll(patternsAfter);
        }

        List<Long> loggerId = patternLoggerSender.afterDeArchive(patternsAfter);

        if (patternsAfter.get(0).getId() > 0) {
            for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size() && i < loggerId.size(); i++) {
                patternBeforeAfter.afterDeArchive(patternsBefore.get(i),
                        patternsAfter.get(i),
                        loggerId.get(i));
            }
        }

        return patternsAfter;
    }

    @Override
    public Pattern updatePattern(Pattern pattern) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (pattern.getId() == null) {
            afterUpdate = Pattern.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(pattern.getId())) {
            afterUpdate = Pattern.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else if (!patternValidator.isValid(pattern)) {
            afterUpdate = Pattern.builder().id((long) -1).build();
            beforeUpdate = afterUpdate;
        } else {

            beforeUpdate = new Pattern(patternRepo.findById((long) pattern.getId()));

            pattern.setLastUpdate(LocalDateTime.now());
            pattern.setDateCreation(beforeUpdate.getDateCreation());
            pattern.setDateActivation(beforeUpdate.getDateActivation());
            pattern.setDateDeactivation(beforeUpdate.getDateDeactivation());

            afterUpdate = patternRepo.save(pattern);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }

        return afterUpdate;
    }

    @Override
    public void incrementFiles(List<Long> id) {
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long)v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            incrementFiles(pattern.getId(), pattern.getArchiveFileCount());
        }
    }

    @Override
    public void incrementFiles(Long id, int count) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (id == null) {
            afterUpdate = Pattern.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(id)) {
            afterUpdate = Pattern.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else {
            afterUpdate = patternRepo.findById((long) id);
            beforeUpdate = new Pattern(afterUpdate);

            afterUpdate.setFileCount(afterUpdate.getFileCount() + count);
            afterUpdate.setArchiveFileCount(afterUpdate.getArchiveFileCount() - count);

            patternRepo.save(afterUpdate);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }
    }

    @Override
    public void decrementFiles(List<Long> id) {
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long)v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            decrementFiles(pattern.getId(), pattern.getFileCount());
        }
    }

    @Override
    public void decrementFiles(Long id, int count) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (id == null) {
            afterUpdate = Pattern.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(id)) {
            afterUpdate = Pattern.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else {
            afterUpdate = patternRepo.findById((long) id);
            beforeUpdate = new Pattern(afterUpdate);

            afterUpdate.setFileCount(afterUpdate.getFileCount() - count);
            afterUpdate.setArchiveFileCount(afterUpdate.getArchiveFileCount() + count);

            patternRepo.save(afterUpdate);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }
    }

}
