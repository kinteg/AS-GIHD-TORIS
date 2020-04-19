package ru.iac.ASGIHDTORIS.spring.service.source;

import org.apache.commons.text.RandomStringGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.iac.ASGIHDTORIS.InterfaceTest;
import ru.iac.ASGIHDTORIS.common.validator.validator.Validator;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.service.source.logger.SourceLoggerService;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

class SourceServiceImplTest implements InterfaceTest {

    @Autowired
    private SourceService sourceService;

    @MockBean
    private SourceRepo sourceRepo;

    @MockBean
    private SourceLoggerService sourceLoggerService;

    @MockBean
    private Validator<Source> validator;


    @Test
    void createSourceTestCompleted() {
        Source sourceBefore = sourceBuilder();

        doReturn(true)
                .when(validator)
                .isValid(sourceBefore);

        doReturn(false)
                .when(sourceRepo)
                .existsByShortName(sourceBefore.getShortName());

        Source sourceAfter = sourceService.createSource(sourceBefore);

        verify(sourceLoggerService, Mockito.times(1)).createLogSourceCreate(sourceAfter);
        verify(sourceRepo, Mockito.times(1)).save(sourceBefore);
    }


    @Test
    void createSourceTestFailed() {
        Source sourceBefore = sourceBuilder();
        System.out.println(sourceBefore.getShortName());

        doReturn(true)
                .when(sourceRepo)
                .existsByShortName(sourceBefore.getShortName());

        Source sourceAfter = sourceService.createSource(sourceBefore);

        boolean isCreated = sourceAfter.getId() > 0;

        verify(sourceLoggerService, Mockito.times(1)).createLogSourceCreate(sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(sourceBefore);
        assertFalse(isCreated);
    }

    @Test
    void archiveSourceTestCompleted() {
        Source source = sourceBuilder();
        Source sourceBefore = Source
                .builder()
                .isArchive(source.getIsArchive())
                .dateDeactivation(source.getDateDeactivation())
                .build();

        Long id = source.getId();

        doReturn(true)
                .when(sourceRepo)
                .existsById(id);

        doReturn(source)
                .when(sourceRepo)
                .findById((long) id);

        Source sourceAfter = sourceService.archiveSource(id);


        assertTrue(sourceAfter.getIsArchive());
        verify(sourceRepo, Mockito.times(1)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceArchive(sourceBefore, sourceAfter);
    }

    @Test
    void archiveSourceTestFailedByNullId() {
        Source sourceBefore = Source.builder().id((long) -4).build();

        Long id = null;

        Source sourceAfter = sourceService.archiveSource(id);

        assertNull(sourceAfter.getIsArchive());
        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceArchive(sourceBefore, sourceAfter);
    }

    @Test
    void archiveSourceTestFailedByIdNotExist() {
        Source source = sourceBuilder();
        Source sourceBefore = Source.builder().id((long) -3).build();

        Long id = source.getId();

        doReturn(false)
                .when(sourceRepo)
                .existsById(id);

        Source sourceAfter = sourceService.archiveSource(id);

        assertNull(sourceAfter.getIsArchive());
        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceArchive(sourceBefore, sourceAfter);
    }

    @Test
    void deArchiveSourceTestCompleted() {
        Source source = sourceBuilder();
        source.setIsArchive(true);

        Source sourceBefore = Source
                .builder()
                .isArchive(source.getIsArchive())
                .dateActivation(source.getDateActivation())
                .build();


        Long id = source.getId();

        doReturn(true)
                .when(sourceRepo)
                .existsById(id);

        doReturn(source)
                .when(sourceRepo)
                .findById((long) id);

        Source sourceAfter = sourceService.deArchiveSource(id);


        assertFalse(sourceAfter.getIsArchive());
        verify(sourceRepo, Mockito.times(1)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceDeArchive(sourceBefore, sourceAfter);
    }

    @Test
    void deArchiveSourceTestFailedByNullId() {
        Source sourceBefore = Source.builder().id((long) -4).build();

        Long id = null;

        Source sourceAfter = sourceService.deArchiveSource(id);

        assertNull(sourceAfter.getIsArchive());
        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceDeArchive(sourceBefore, sourceAfter);
    }

    @Test
    void deArchiveSourceTestFailedByIdNotExist() {
        Source source = sourceBuilder();
        source.setIsArchive(true);
        Source sourceBefore = Source.builder().id((long) -3).build();

        Long id = source.getId();

        doReturn(false)
                .when(sourceRepo)
                .existsById(id);

        Source sourceAfter = sourceService.deArchiveSource(id);

        assertNull(sourceAfter.getIsArchive());
        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceDeArchive(sourceBefore, sourceAfter);
    }

    @Test
    void updateSourceTestCompleted() {
        Source sourceBefore = sourceBuilder();
        Source source = sourceBuilder();
        source.setId(sourceBefore.getId());

        doReturn(true)
                .when(sourceRepo)
                .existsById(source.getId());

        doReturn(true)
                .when(validator)
                .isValid(source);

        doReturn(true)
                .when(sourceRepo)
                .existsById(source.getId());

        doReturn(false)
                .when(sourceRepo)
                .existsByShortName(source.getShortName());

        doReturn(source)
                .when(sourceRepo)
                .findById((long)source.getId());

        Source sourceAfter = sourceService.updateSource(source);

//        assertNull(sourceAfter);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceUpdate(source, sourceAfter);
        verify(sourceRepo, Mockito.times(1)).save(source);
    }

    @Test
    void updateSourceTestFailedByNullId() {
        Source sourceBefore = Source.builder().id((long) -4).build();

        Source source = new Source();

        Source sourceAfter = sourceService.updateSource(source);

        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(source);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceUpdate(sourceBefore, sourceAfter);
    }

    @Test
    void updateSourceTestFailedByNotExistId() {
        Source sourceBefore = Source.builder().id((long) -3).build();

        Source source = new Source();
        source.setId(1L);

        doReturn(false)
                .when(sourceRepo)
                .existsById(source.getId());

        Source sourceAfter = sourceService.updateSource(source);

        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(source);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceUpdate(sourceBefore, sourceAfter);
    }

    @Test
    void updateSourceTestFailedBySourceNotValid() {
        Source sourceBefore = Source.builder().id((long) -1).build();

        Source source = new Source();
        source.setId(1L);

        doReturn(true)
                .when(sourceRepo)
                .existsById(source.getId());

        doReturn(false)
                .when(validator)
                .isValid(source);

        Source sourceAfter = sourceService.updateSource(source);

        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(source);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceUpdate(sourceBefore, sourceAfter);
    }

    @Test
    void updateSourceTestFailedByExistShortNameAndNotExistByShortNameAndId() {
        Source sourceBefore = Source.builder().id((long) -2).build();

        Source source = new Source();
        source.setId(1L);

        doReturn(true)
                .when(sourceRepo)
                .existsById(source.getId());

        doReturn(true)
                .when(validator)
                .isValid(source);

        doReturn(true)
                .when(sourceRepo)
                .existsByShortName(source.getShortName());

        doReturn(false)
                .when(sourceRepo)
                .existsByShortNameAndId(source.getShortName(), source.getId());

        Source sourceAfter = sourceService.updateSource(source);

        assertEquals(sourceBefore, sourceAfter);
        verify(sourceRepo, Mockito.times(0)).save(source);
        verify(sourceLoggerService, Mockito.times(1)).createLogSourceUpdate(sourceBefore, sourceAfter);
    }


    private Source sourceBuilder() {

        RandomStringGenerator generator =
                new RandomStringGenerator
                        .Builder()
                        .withinRange('a', 'z')
                        .build();

        return Source.builder()
                .id(new Random().nextLong())
                .name(generator.generate(10))
                .longName(generator.generate(10))
                .shortName(generator.generate(10))
                .description(generator.generate(10))
                .addDescription(generator.generate(10))
                .scope(generator.generate(10))
                .periodicity(generator.generate(10))
                .renewalPeriod(generator.generate(10))
                .type(generator.generate(10))
                .tags(generator.generate(10))
                .providerLink(generator.generate(10))
                .dataSource(generator.generate(10))
                .dateCreation(LocalDateTime.now())
                .dateActivation(LocalDateTime.now())
                .dateDeactivation(LocalDateTime.now())
                .lastUpdate(LocalDateTime.now())
                .isArchive(false)
                .build();
    }

}