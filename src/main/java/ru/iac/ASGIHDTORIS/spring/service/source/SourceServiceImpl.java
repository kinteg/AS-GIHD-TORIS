package ru.iac.ASGIHDTORIS.spring.service.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.domain.SourceSet;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceSetRepo;
import ru.iac.ASGIHDTORIS.spring.service.source.logger.SourceLoggerService;

@Service
@Slf4j
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;
    private final SourceLoggerService sourceLoggerService;
    private final SourceSetRepo sourceSetRepo;

    public SourceServiceImpl(
            SourceRepo sourceRepo,
            SourceLoggerService sourceLoggerService, SourceSetRepo sourceSetRepo) {
        this.sourceRepo = sourceRepo;
        this.sourceLoggerService = sourceLoggerService;
        this.sourceSetRepo = sourceSetRepo;
    }

    public Source createSource(Source source, User user) {
        if (user.isEmpty()) {
            return Source.getBadIdSource(-1);
        }
        source.setCreateTime();
        return buildSource(source, user);
    }

    @Override
    public Source archiveSource(Long id, User user) {
        return buildArchiveSource(id, user);
    }

    @Override
    public Source deArchiveSource(Long id, User user) {
        return buildDeArchiveSource(id, user);
    }

    @Override
    public Source updateSource(Source source, User user) {
        return buildUpdateSource(source, user);
    }

    private Source buildSource(Source source, User user) {
        Source sourceAfter;

        if (!sourceRepo.existsByShortName(source.getShortName())) {
            sourceAfter = saveCreateSource(source);
        } else {
            return Source.getBadIdSource(-2);
        }

        sourceLoggerService.createLogSourceCreate(sourceAfter, user);

        sourceSetRepo.save(SourceSet.builder()
                .sourceId(sourceAfter.getId())
                .userId(user.getId()).build());

        return sourceAfter;
    }

    private Source buildArchiveSource(long id, User user) {
        if (sourceRepo.existsById(id)) {
            return saveArchiveSource(id, user);
        } else {
            return buildBadArchiveSource(user);
        }
    }

    private Source buildDeArchiveSource(long id, User user) {
        if (sourceRepo.existsById(id)) {
            return saveDeArchiveSource(id, user);
        } else {
            return buildBadDeArchiveSource(user);
        }
    }

    private Source buildUpdateSource(Source source, User user) {
        if (!invalid(source)) {
            return saveUpdateSource(source, user);
        } else {
            return buildInvalidSource(source, user);
        }
    }

    private Source saveCreateSource(Source source) {
        try {
            return sourceRepo.save(source);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return Source.getBadIdSource(-1);
        }
    }

    private Source saveArchiveSource(long id, User user) {
        Source sourceAfter = sourceRepo.findById(id);
        Source sourceBefore = Source.getArchiveInfo(sourceAfter);

        sourceAfter.archive();

        sourceRepo.save(sourceAfter);

        saveArchiveLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source saveDeArchiveSource(long id, User user) {
        Source sourceAfter = sourceRepo.findById(id);
        Source sourceBefore = Source.getDeArchiveInfo(sourceAfter);

        sourceAfter.deArchive();

        sourceRepo.save(sourceAfter);

        saveDeArchiveLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source saveUpdateSource(Source source, User user) {
        Source beforeUpdate = new Source(sourceRepo.findById((long) source.getId()));

        source.setUpdateTime(beforeUpdate);

        Source afterUpdate = sourceRepo.save(source);

        sourceLoggerService.createLogSourceUpdate(beforeUpdate, afterUpdate, user);

        return afterUpdate;
    }

    private Source buildInvalidSource(Source source, User user) {
        if (source.getId() == null) {
            return buildBadIdSource(user);

        } else if (!sourceRepo.existsById(source.getId())) {
            return buildNotExistSource(user);

        } else {
            return buildBadNameSource(user);
        }
    }

    private Source buildBadArchiveSource(User user) {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveArchiveLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source buildBadDeArchiveSource(User user) {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveDeArchiveLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source buildBadIdSource(User user) {
        Source sourceAfter = Source.getBadIdSource(-4);
        Source sourceBefore = Source.getBadIdSource(-4);

        saveUpdateLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source buildNotExistSource(User user) {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveUpdateLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private Source buildBadNameSource(User user) {
        Source sourceAfter = Source.getBadIdSource(-2);
        Source sourceBefore = Source.getBadIdSource(-2);

        saveUpdateLog(sourceBefore, sourceAfter, user);

        return sourceAfter;
    }

    private boolean invalid(Source source) {
        return source.getId() == null ||
                !sourceRepo.existsById(source.getId()) ||
                (
                        sourceRepo.existsByShortName(source.getShortName()) &&
                                !sourceRepo.existsByShortNameAndId(source.getShortName(), source.getId())
                );
    }

    private void saveArchiveLog(Source before, Source after, User user) {
        sourceLoggerService.createLogSourceArchive(before, after, user);
    }

    private void saveDeArchiveLog(Source before, Source after, User user) {
        sourceLoggerService.createLogSourceDeArchive(before, after, user);
    }

    private void saveUpdateLog(Source before, Source after, User user) {
        sourceLoggerService.createLogSourceUpdate(before, after, user);
    }

}
