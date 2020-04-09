package ru.iac.ASGIHDTORIS.spring.service.source;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.component.logger.LoggerSender;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;

import java.time.LocalDateTime;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;
    private final LoggerSender<Source> sourceLoggerSender;
    private final BeforeAfter<Source> sourceBeforeAfter;
    private final Validator<Source> validator;

    public SourceServiceImpl(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator,
            @Qualifier("sourceLoggerSender") LoggerSender<Source> sourceLoggerSender,
            @Qualifier("sourceBeforeAfter") BeforeAfter<Source> sourceBeforeAfter) {
        this.sourceRepo = sourceRepo;
        this.sourceLoggerSender = sourceLoggerSender;
        this.sourceBeforeAfter = sourceBeforeAfter;
        this.validator = validator;
    }

    public Source createSource(Source source) {
        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        ru.iac.ASGIHDTORIS.spring.domain.Source sourceAfter = validator.isValid(source)
                ? !sourceRepo.existsByShortName(source.getShortName())
                ? sourceRepo.save(source)
                : ru.iac.ASGIHDTORIS.spring.domain.Source.builder().id(Long.parseLong("-2")).build()
                : ru.iac.ASGIHDTORIS.spring.domain.Source.builder().id(Long.parseLong("-1")).build();

        long loggerId = sourceLoggerSender.afterCreate(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterCreate(sourceAfter, loggerId);
        }

        return sourceAfter;
    }

    @Override
    public Source archiveSource(Long id) {
        Source sourceBefore, sourceAfter;

        if (id == null) {
            sourceAfter = Source.builder().id((long) -4).build();
            sourceBefore = sourceAfter;
        } else if (!sourceRepo.existsById(id)) {
            sourceAfter = Source.builder().id((long) -3).build();
            sourceBefore = sourceAfter;
        } else {
            sourceAfter = sourceRepo.findById((long) id);
            sourceBefore = Source
                    .builder()
                    .isArchive(sourceAfter.getIsArchive())
                    .dateDeactivation(sourceAfter.getDateDeactivation())
                    .build();
            sourceAfter.setIsArchive(true);
            sourceAfter.setDateDeactivation(LocalDateTime.now());

            sourceRepo.save(sourceAfter);

        }

        long loggerId = sourceLoggerSender.afterArchive(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterArchive(sourceBefore, sourceAfter, loggerId);
        }

        return sourceAfter;
    }

    @Override
    public Source deArchiveSource(Long id) {
        Source sourceBefore, sourceAfter;

        if (id == null) {
            sourceAfter = Source.builder().id((long) -4).build();
            sourceBefore = sourceAfter;
        } else if (!sourceRepo.existsById(id)) {
            sourceAfter = Source.builder().id((long) -3).build();
            sourceBefore = sourceAfter;
        } else {
            sourceAfter = sourceRepo.findById((long) id);
            sourceBefore = Source
                    .builder()
                    .isArchive(sourceAfter.getIsArchive())
                    .dateActivation(sourceAfter.getDateActivation())
                    .build();

            sourceAfter.setIsArchive(false);
            sourceAfter.setDateActivation(LocalDateTime.now());

            sourceRepo.save(sourceAfter);

        }

        long loggerId = sourceLoggerSender.afterDeArchive(sourceAfter);

        if (sourceAfter.getId() > 0) {
            sourceBeforeAfter.afterDeArchive(sourceBefore, sourceAfter, loggerId);
        }

        return sourceAfter;
    }

    @Override
    public Source updateSource(Source source) {
        Source afterUpdate;
        Source beforeUpdate;

        if (source.getId() == null) {
            afterUpdate = Source.builder().id((long) -4).build();
            beforeUpdate = afterUpdate;
        } else if (!sourceRepo.existsById(source.getId())) {
            afterUpdate = Source.builder().id((long) -3).build();
            beforeUpdate = afterUpdate;
        } else if (!validator.isValid(source)) {
            afterUpdate = Source.builder().id((long) -1).build();
            beforeUpdate = afterUpdate;
        } else if (sourceRepo.existsByShortName(source.getShortName())
                && !sourceRepo.existsByShortNameAndId(
                source.getShortName(),
                source.getId())) {

            afterUpdate = Source.builder().id((long) -2).build();
            beforeUpdate = afterUpdate;
        } else {

            beforeUpdate = new Source(sourceRepo.findById((long) source.getId()));

            source.setLastUpdate(LocalDateTime.now());
            source.setDateCreation(beforeUpdate.getDateCreation());
            source.setDateActivation(beforeUpdate.getDateActivation());
            source.setDateDeactivation(beforeUpdate.getDateDeactivation());

            afterUpdate = sourceRepo.save(source);
        }

        long loggerId = sourceLoggerSender.afterUpdate(afterUpdate);

        if (afterUpdate.getId() > 0) {
            sourceBeforeAfter.afterUpdate(beforeUpdate, afterUpdate, loggerId);
        }

        return afterUpdate;
    }

}
