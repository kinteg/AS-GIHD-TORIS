package ru.iac.ASGIHDTORIS.spring.service.pattern;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.repo.PatternRepo;
import ru.iac.ASGIHDTORIS.spring.service.pattern.logger.PatternLoggerService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatternServiceImpl implements PatternService {

    private final PatternRepo patternRepo;
    private final Validator<Pattern> patternValidator;
    private final PatternLoggerService patternLoggerService;

    public PatternServiceImpl(
            PatternRepo patternRepo,
            @Qualifier("getPatternValidator") Validator<Pattern> patternValidator,
            PatternLoggerService patternLoggerService) {

        this.patternRepo = patternRepo;
        this.patternValidator = patternValidator;
        this.patternLoggerService = patternLoggerService;
    }

    @Override
    public Pattern createPattern(Pattern pattern, User user) {
        pattern.setCreate();
        return buildCreatePattern(pattern, user);
    }

    @Override
    public Pattern archivePattern(Long id, User user) {
        return buildArchivePattern(id, user);
    }

    @Override
    public Pattern deArchivePattern(Long id, User user) {
        return buildDeArchivePattern(id, user);
    }

    @Override
    public List<Pattern> archivePatterns(Long sourceId, User user) {
        return buildArchivePatterns(sourceId, user);
    }

    @Override
    public List<Pattern> deArchivePatterns(Long sourceId, User user) {
        return buildDeArchivePatterns(sourceId, user);
    }

    @Override
    public Pattern updatePattern(Pattern pattern, User user) {
        return buildUpdatePattern(pattern, user);
    }

    private Pattern buildUpdatePattern(Pattern pattern, User user) {
        if (!invalid(pattern)) {
            return saveUpdatePattern(pattern, user);
        } else {
            return buildInvalidPattern(pattern, user);
        }
    }

    private Pattern buildInvalidPattern(Pattern pattern, User user) {
        Pattern afterUpdate;
        Pattern beforeUpdate;

        if (pattern.getId() == null) {
            afterUpdate = Pattern.getBadIdPattern(-4);
            beforeUpdate = afterUpdate;
        } else if (!patternRepo.existsById(pattern.getId())) {
            afterUpdate = Pattern.getBadIdPattern(-3);
            beforeUpdate = afterUpdate;
        } else {
            afterUpdate = Pattern.getBadIdPattern(-1);
            beforeUpdate = afterUpdate;
        }

        patternLoggerService.createLogPatternUpdate(beforeUpdate, afterUpdate, user);

        return afterUpdate;
    }

    private Pattern saveUpdatePattern(Pattern pattern, User user) {
        Pattern beforeUpdate = new Pattern(patternRepo.findById((long) pattern.getId()));

        pattern.setUpdate(beforeUpdate);

        Pattern afterUpdate = patternRepo.save(pattern);

        patternLoggerService.createLogPatternUpdate(beforeUpdate, afterUpdate, user);

        return afterUpdate;
    }

    private boolean invalid(Pattern pattern) {
        return pattern.getId() == null ||
                !patternRepo.existsById(pattern.getId()) ||
                !patternValidator.isValid(pattern);
    }

    @Override
    public void incrementFiles(List<Long> id, User user) {
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long) v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            incrementFiles(pattern.getId(), pattern.getArchiveFileCount(), user);
        }
    }

    @Override
    public void incrementFiles(Long id, int count, User user) {
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

        patternLoggerService.createLogIncrement(beforeUpdate, afterUpdate, user);
    }

    @Override
    public void decrementFiles(List<Long> id, User user) {
        List<Pattern> patternTables = id.stream().map(v -> patternRepo.findById((long) v)).collect(Collectors.toList());

        for (Pattern pattern :
                patternTables) {
            decrementFiles(pattern.getId(), pattern.getFileCount(), user);
        }
    }

    @Override
    public void decrementFiles(Long id, int count, User user) {
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

        patternLoggerService.createLogDecrement(beforeUpdate, afterUpdate, user);
    }

    private Pattern buildCreatePattern(Pattern pattern, User user) {
        if (patternValidator.isValid(pattern)) {
            patternRepo.save(pattern);
        } else {
            pattern = Pattern.getBadIdPattern(-1);
        }

        patternLoggerService.createLogCreate(pattern, user);

        return pattern;
    }

    private Pattern buildArchivePattern(long id, User user) {
        if (patternRepo.existsById(id)) {
            return saveArchive(id, user);
        } else {
            return buildBadIdArchive(user);
        }
    }

    private Pattern buildDeArchivePattern(long id, User user) {
        if (patternRepo.existsById(id)) {
            return saveDeArchive(id, user);
        } else {
            return buildBadIdDeArchive(user);
        }
    }

    private List<Pattern> buildArchivePatterns(long sourceId, User user) {
        if (patternRepo.existsBySourceId(sourceId)) {
            return saveArchives(sourceId, user);
        } else {
            return buildBadIdArchives(user);
        }
    }

    private List<Pattern> buildDeArchivePatterns(long sourceId, User user) {
        if (patternRepo.existsBySourceId(sourceId)) {
            return saveDeArchives(sourceId, user);
        } else {
            return buildBadIdDeArchives(user);
        }
    }

    private Pattern saveArchive(long id, User user) {
        Pattern patternAfter = patternRepo.findById(id);
        Pattern patternBefore = Pattern.getArchiveInfo(patternAfter);

        patternAfter.archive();

        patternRepo.save(patternAfter);

        patternLoggerService.createLogPatternArchive(patternBefore, patternAfter, user);

        return patternAfter;
    }

    private List<Pattern> saveArchives(long sourceId, User user) {
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

        patternLoggerService.createLogPatternsArchive(patternsBefore, patternsAfter, user);

        return patternsAfter;
    }

    private Pattern saveDeArchive(long id, User user) {
        Pattern patternAfter = patternRepo.findById(id);
        Pattern patternBefore = Pattern.getDeArchiveInfo(patternAfter);

        patternAfter.deArchive();

        patternRepo.save(patternAfter);

        patternLoggerService.createLogPatternDeArchive(patternBefore, patternAfter, user);

        return patternAfter;
    }

    private List<Pattern> saveDeArchives(long sourceId, User user) {
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

        patternLoggerService.createLogPatternsDeArchive(patternsBefore, patternsAfter, user);

        return patternsAfter;
    }

    private Pattern buildBadIdArchive(User user) {
        Pattern patternAfter = Pattern.getBadIdPattern(-3);
        Pattern patternBefore = Pattern.getBadIdPattern(-3);

        patternLoggerService.createLogPatternArchive(patternBefore, patternAfter, user);

        return patternAfter;
    }

    private List<Pattern> buildBadIdArchives(User user) {
        List<Pattern> patternsAfter = Collections.singletonList(Pattern.getBadIdPattern(-3));
        List<Pattern> patternBefore = Collections.singletonList(Pattern.getBadIdPattern(-3));

        patternLoggerService.createLogPatternArchive(patternBefore.get(0), patternsAfter.get(0), user);

        return patternsAfter;
    }

    private Pattern buildBadIdDeArchive(User user) {
        Pattern patternAfter = Pattern.getBadIdPattern(-3);
        Pattern patternBefore = Pattern.getBadIdPattern(-3);

        patternLoggerService.createLogPatternDeArchive(patternBefore, patternAfter, user);

        return patternAfter;
    }

    private List<Pattern> buildBadIdDeArchives(User user) {
        List<Pattern> patternsAfter = Collections.singletonList(Pattern.getBadIdPattern(-3));
        List<Pattern> patternBefore = Collections.singletonList(Pattern.getBadIdPattern(-3));

        patternLoggerService.createLogPatternDeArchive(patternBefore.get(0), patternsAfter.get(0), user);

        return patternsAfter;
    }


}
