package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;

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

    @Override
    public Pattern createPattern(Pattern pattern) {
        pattern.setCreate();
        return buildCreatePattern(pattern);
    }

    @Override
    public Pattern archivePattern(Long id) {
        return buildArchivePattern(id);
    }

    @Override
    public Pattern deArchivePattern(Long id) {
        return buildDeArchivePattern(id);
    }

    @Override
    public List<Pattern> archivePatterns(Long sourceId) {
        return buildArchivePatterns(sourceId);
    }

    @Override
    public List<Pattern> deArchivePatterns(Long sourceId) {
        return buildDeArchivePatterns(sourceId);
    }

    @Override
    public Pattern updatePattern(Pattern pattern) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (pattern.getId() == null) {
            afterUpdate = Pattern.getBadIdPattern(-4);
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(pattern.getId())) {
            afterUpdate = Pattern.getBadIdPattern(-3);
            beforeUpdate = afterUpdate;
        } else if (!patternValidator.isValid(pattern)) {
            afterUpdate = Pattern.getBadIdPattern(-1);
            beforeUpdate = afterUpdate;
        } else {

            beforeUpdate = new Pattern(patternRepo.findById((long) pattern.getId()));

            pattern.setUpdate(beforeUpdate);

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
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long) v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            incrementFiles(pattern.getId(), pattern.getArchiveFileCount());
        }
    }

    @Override
    public void incrementFiles(Long id, int count) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (!patternRepo.existsById(id)) {
            afterUpdate = Pattern.getBadIdPattern(-3);
            beforeUpdate = afterUpdate;
        } else {
            afterUpdate = patternRepo.findById((long) id);
            beforeUpdate = new Pattern(afterUpdate);

            afterUpdate.incrementFiles(count);

            patternRepo.save(afterUpdate);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }
    }

    @Override
    public void decrementFiles(List<Long> id) {
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long) v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            decrementFiles(pattern.getId(), pattern.getFileCount());
        }
    }

    @Override
    public void decrementFiles(Long id, int count) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (!patternRepo.existsById(id)) {
            afterUpdate = Pattern.getBadIdPattern(-3);
            beforeUpdate = afterUpdate;
        } else {
            afterUpdate = patternRepo.findById((long) id);
            beforeUpdate = new Pattern(afterUpdate);

            afterUpdate.decrementFiles(count);

            patternRepo.save(afterUpdate);
        }

        long loggerId = patternLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            patternBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }
    }

    private Pattern buildCreatePattern(Pattern pattern) {
        if (patternValidator.isValid(pattern)) {
            patternRepo.save(pattern);
        } else {
            pattern = Pattern.getBadIdPattern(-1);
        }

        createCreateLog(pattern);

        return pattern;
    }

    private Pattern buildArchivePattern(long id) {
        if (patternRepo.existsById(id)) {
            return saveArchive(id);
        } else {
            return buildBadIdArchive();
        }
    }

    private Pattern buildDeArchivePattern(long id) {
        if (patternRepo.existsById(id)) {
            return saveDeArchive(id);
        } else {
            return buildBadIdDeArchive();
        }
    }

    private List<Pattern> buildArchivePatterns(long sourceId) {
        if (patternRepo.existsBySourceId(sourceId)) {
            return saveArchives(sourceId);
        } else {
            return buildBadIdArchives();
        }
    }

    private List<Pattern> buildDeArchivePatterns(long sourceId) {
        if (patternRepo.existsBySourceId(sourceId)) {
            return saveDeArchives(sourceId);
        } else {
            return buildBadIdDeArchives();
        }
    }

    private Pattern saveArchive(long id) {
        Pattern patternAfter = patternRepo.findById(id);
        Pattern patternBefore = Pattern.getArchiveInfo(patternAfter);

        patternAfter.archive();

        patternRepo.save(patternAfter);

        createArchiveLog(patternBefore, patternAfter);

        return patternAfter;
    }

    private List<Pattern> saveArchives(long sourceId) {
        List<Pattern> patternsBefore = patternRepo
                .findAllBySourceId(sourceId)
                .stream()
                .map(Pattern::getArchiveInfo)
                .collect(Collectors.toList());

        List<Pattern> patternsAfter = patternRepo
                .findAllBySourceId(sourceId)
                .stream()
                .peek(Pattern::archive)
                .collect(Collectors.toList());

        patternRepo.saveAll(patternsAfter);

        for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size(); i++) {
            createArchiveLog(patternsBefore.get(i), patternsAfter.get(i));
        }

        return patternsAfter;
    }

    private Pattern saveDeArchive(long id) {
        Pattern patternAfter = patternRepo.findById(id);
        Pattern patternBefore = Pattern.getDeArchiveInfo(patternAfter);

        patternAfter.deArchive();

        patternRepo.save(patternAfter);

        createDeArchiveLog(patternBefore, patternAfter);

        return patternAfter;
    }

    private List<Pattern> saveDeArchives(long sourceId) {
        List<Pattern> patternsBefore = patternRepo
                .findAllBySourceId(sourceId)
                .stream()
                .map(Pattern::getDeArchiveInfo)
                .collect(Collectors.toList());

        List<Pattern> patternsAfter = patternRepo
                .findAllBySourceId(sourceId)
                .stream()
                .peek(Pattern::deArchive)
                .collect(Collectors.toList());

        patternRepo.saveAll(patternsAfter);

        for (int i = 0; i < patternsAfter.size() && i < patternsBefore.size(); i++) {
            createDeArchiveLog(patternsBefore.get(i), patternsAfter.get(i));
        }

        return patternsAfter;
    }

    private Pattern buildBadIdArchive() {
        Pattern patternAfter = Pattern.getBadIdPattern(-3);
        Pattern patternBefore = Pattern.getBadIdPattern(-3);

        createArchiveLog(patternBefore, patternAfter);

        return patternAfter;
    }

    private List<Pattern> buildBadIdArchives() {
        List<Pattern> patternsAfter = Collections.singletonList(Pattern.getBadIdPattern(-3));
        List<Pattern> patternBefore = Collections.singletonList(Pattern.getBadIdPattern(-3));

        createArchiveLog(patternBefore.get(0), patternsAfter.get(0));

        return patternsAfter;
    }

    private Pattern buildBadIdDeArchive() {
        Pattern patternAfter = Pattern.getBadIdPattern(-3);
        Pattern patternBefore = Pattern.getBadIdPattern(-3);

        createArchiveLog(patternBefore, patternAfter);

        return patternAfter;
    }

    private List<Pattern> buildBadIdDeArchives() {
        List<Pattern> patternsAfter = Collections.singletonList(Pattern.getBadIdPattern(-3));
        List<Pattern> patternBefore = Collections.singletonList(Pattern.getBadIdPattern(-3));

        createDeArchiveLog(patternBefore.get(0), patternsAfter.get(0));

        return patternsAfter;
    }

    private void createCreateLog(Pattern patternAfter) {
        long loggerId = patternLoggerSender.afterCreate(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterCreate(patternAfter, loggerId);
        }
    }

    private void createArchiveLog(Pattern patternBefore, Pattern patternAfter) {
        long loggerId = patternLoggerSender.afterArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterArchive(patternBefore, patternAfter, loggerId);
        }
    }

    private void createDeArchiveLog(Pattern patternBefore, Pattern patternAfter) {
        long loggerId = patternLoggerSender.afterDeArchive(patternAfter);

        if (patternAfter.getId() > 0) {
            patternBeforeAfter.afterDeArchive(patternBefore, patternAfter, loggerId);
        }
    }


}
