package ru.iac.ASGIHDTORIS.spring.service.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.service.source.logger.SourceLoggerService;

@Service
@Slf4j
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;
    private final SourceLoggerService sourceLoggerService;

    public SourceServiceImpl(
            SourceRepo sourceRepo,
            SourceLoggerService sourceLoggerService) {
        this.sourceRepo = sourceRepo;
        this.sourceLoggerService = sourceLoggerService;
    }

    public Source createSource(Source source) {
        source.setCreateTime();
        return buildSource(source);
    }

    @Override
    public Source archiveSource(Long id) {
        return buildArchiveSource(id);
    }

    @Override
    public Source deArchiveSource(Long id) {
        return buildDeArchiveSource(id);
    }

    @Override
    public Source updateSource(Source source) {
        return buildUpdateSource(source);
    }

    private Source buildSource(Source source) {
        Source sourceAfter;

        if (!sourceRepo.existsByShortName(source.getShortName())) {
            sourceAfter = saveCreateSource(source);
        } else {
            return Source.getBadIdSource(-2);
        }

        sourceLoggerService.createLogSourceCreate(sourceAfter);

        return sourceAfter;
    }

    private Source buildArchiveSource(long id) {
        if (sourceRepo.existsById(id)) {
            return saveArchiveSource(id);
        } else {
            return buildBadArchiveSource();
        }
    }

    private Source buildDeArchiveSource(long id) {
        if (sourceRepo.existsById(id)) {
            return saveDeArchiveSource(id);
        } else {
            return buildBadDeArchiveSource();
        }
    }

    private Source buildUpdateSource(Source source) {
        if (!invalid(source)) {
            return saveUpdateSource(source);
        } else {
            return buildInvalidSource(source);
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

    private Source saveArchiveSource(long id) {
        Source sourceAfter = sourceRepo.findById(id);
        Source sourceBefore = Source.getArchiveInfo(sourceAfter);

        sourceAfter.archive();

        sourceRepo.save(sourceAfter);

        saveArchiveLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source saveDeArchiveSource(long id) {
        Source sourceAfter = sourceRepo.findById(id);
        Source sourceBefore = Source.getDeArchiveInfo(sourceAfter);

        sourceAfter.deArchive();

        sourceRepo.save(sourceAfter);

        saveDeArchiveLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source saveUpdateSource(Source source) {
        Source beforeUpdate = new Source(sourceRepo.findById((long) source.getId()));

        source.setUpdateTime(beforeUpdate);

        Source afterUpdate = sourceRepo.save(source);

        sourceLoggerService.createLogSourceUpdate(beforeUpdate, afterUpdate);

        return afterUpdate;
    }

    private Source buildInvalidSource(Source source) {
        if (source.getId() == null) {
            return buildBadIdSource();

        } else if (!sourceRepo.existsById(source.getId())) {
            return buildNotExistSource();

        } else {
            return buildBadNameSource();
        }
    }

    private Source buildBadArchiveSource() {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveArchiveLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source buildBadDeArchiveSource() {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveDeArchiveLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source buildBadIdSource() {
        Source sourceAfter = Source.getBadIdSource(-4);
        Source sourceBefore = Source.getBadIdSource(-4);

        saveUpdateLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source buildNotExistSource() {
        Source sourceAfter = Source.getBadIdSource(-3);
        Source sourceBefore = Source.getBadIdSource(-3);

        saveUpdateLog(sourceBefore, sourceAfter);

        return sourceAfter;
    }

    private Source buildBadNameSource() {
        Source sourceAfter = Source.getBadIdSource(-2);
        Source sourceBefore = Source.getBadIdSource(-2);

        saveUpdateLog(sourceBefore, sourceAfter);

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

    private void saveArchiveLog(Source before, Source after) {
        sourceLoggerService.createLogSourceArchive(before, after);
    }

    private void saveDeArchiveLog(Source before, Source after) {
        sourceLoggerService.createLogSourceDeArchive(before, after);
    }

    private void saveUpdateLog(Source before, Source after) {
        sourceLoggerService.createLogSourceUpdate(before, after);
    }

}
