package ru.iac.ASGIHDTORIS.spring.service.source;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.service.source.logger.SourceLoggerService;

import java.time.LocalDateTime;

@Service
@Slf4j
public class SourceServiceImpl implements SourceService {

    private final SourceRepo sourceRepo;
    private final Validator<Source> validator;
    private final SourceLoggerService sourceLoggerService;

    public SourceServiceImpl(
            SourceRepo sourceRepo,
            @Qualifier("getSourceValidator") Validator<Source> validator,
            SourceLoggerService sourceLoggerService) {
        this.sourceRepo = sourceRepo;
        this.validator = validator;
        this.sourceLoggerService = sourceLoggerService;
    }

    public Source createSource(Source source) {
        source.setDateCreation(LocalDateTime.now());
        source.setDateActivation(LocalDateTime.now());
        source.setLastUpdate(LocalDateTime.now());

        Source sourceAfter = validator.isValid(source)
                ? !sourceRepo.existsByShortName(source.getShortName())
                ? sourceRepo.save(source)
                : Source.builder().id(Long.parseLong("-2")).build()
                : Source.builder().id(Long.parseLong("-1")).build();

        sourceLoggerService.createLogSourceCreate(sourceAfter);

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

        sourceLoggerService.createLogSourceArchive(sourceBefore, sourceAfter);

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

        sourceLoggerService.createLogSourceDeArchive(sourceBefore, sourceAfter);

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

        sourceLoggerService.createLogSourceUpdate(beforeUpdate, afterUpdate);

        return afterUpdate;
    }

}
