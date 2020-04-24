package ru.iac.ASGIHDTORIS.spring.component.ba.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPattern;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatternBeforeAfter implements BeforeAfter<Pattern> {

    private final BeforeAfterPatternRepo beforeAfterPatternRepo;

    public PatternBeforeAfter(BeforeAfterPatternRepo beforeAfterPatternRepo) {
        this.beforeAfterPatternRepo = beforeAfterPatternRepo;
    }

    @Override
    public void afterCreate(Pattern after, Long loggerId) {
        List<BeforeAfterPattern> beforeAfterPatterns = new ArrayList<>();

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("id")
                .before("-")
                .after(after.getId().toString())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("name")
                .before("-")
                .after(after.getName())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("file_count")
                .before("-")
                .after(after.getFileCount().toString())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("archive_file_count")
                .before("-")
                .after(after.getArchiveFileCount().toString())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("description")
                .before("-")
                .after(after.getDescription())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("direction")
                .before("-")
                .after(after.getDirection())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("management")
                .before("-")
                .after(after.getManagement())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("date_creation")
                .before("-")
                .after(after.getDateCreation().toString())
                .build());

        if (after.getDateDeactivation() != null) {
            beforeAfterPatterns.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("date_deactivation")
                    .before("-")
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("date_activation")
                .before("-")
                .after(after.getDateActivation().toString())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("last_update")
                .before("-")
                .after(after.getLastUpdate().toString())
                .build());

        beforeAfterPatterns.add(BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("archive")
                .before("-")
                .after(after.getIsArchive().toString())
                .build());

        beforeAfterPatternRepo.saveAll(beforeAfterPatterns);
    }

    @Override
    public void afterArchive(Pattern before, Pattern after, Long loggerId) {
        BeforeAfterPattern beforeAfterSource = BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("archive")
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternRepo.save(beforeAfterSource);

        beforeAfterSource = BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("date_deactivation")
                .before(before.getDateDeactivation() == null ?
                        "-" : before.getDateDeactivation().toString())
                .after(after.getDateDeactivation().toString())
                .build();

        beforeAfterPatternRepo.save(beforeAfterSource);
    }

    @Override
    public void afterDeArchive(Pattern before, Pattern after, Long loggerId) {
        BeforeAfterPattern beforeAfterSource = BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("archive")
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternRepo.save(beforeAfterSource);

        beforeAfterSource = BeforeAfterPattern
                .builder()
                .patternLoggerId(loggerId)
                .columnName("date_activation")
                .before(before.getDateActivation().toString())
                .after(after.getDateActivation().toString())
                .build();

        beforeAfterPatternRepo.save(beforeAfterSource);
    }

    @Override
    public void afterUpdate(Pattern before, Pattern after, Long loggerId) {
        List<BeforeAfterPattern> beforeAfterSources = new ArrayList<>();

        if (!before.getId().equals(after.getId())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("id")
                    .before(before.getId().toString())
                    .after(after.getId().toString())
                    .build());
        }

        if (!before.getName().equals(after.getName())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("name")
                    .before(before.getName())
                    .after(after.getName())
                    .build());
        }

        if (!before.getFileCount().equals(after.getFileCount())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .columnName("file_count")
                    .patternLoggerId(loggerId)
                    .before(before.getFileCount().toString())
                    .after(after.getFileCount().toString())
                    .build());
        }

        if (!before.getArchiveFileCount().equals(after.getArchiveFileCount())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("archive_file_count")
                    .before(before.getArchiveFileCount().toString())
                    .after(after.getArchiveFileCount().toString())
                    .build());
        }

        if (!before.getDescription().equals(after.getDescription())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("description")
                    .before(before.getDescription())
                    .after(after.getDescription())
                    .build());
        }

        if (!before.getDirection().equals(after.getDirection())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("direction")
                    .before(before.getDirection())
                    .after(after.getDirection())
                    .build());
        }

        if (!before.getManagement().equals(after.getManagement())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("management")
                    .before(before.getManagement())
                    .after(after.getManagement())
                    .build());
        }

        if (!before.getDateCreation().equals(after.getDateCreation())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("date_creation")
                    .before(before.getDateCreation().toString())
                    .after(after.getDateCreation().toString())
                    .build());
        }

        if (before.getDateDeactivation() != null && after.getDateDeactivation() != null
                && !before.getDateDeactivation().equals(after.getDateDeactivation())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("date_deactivation")
                    .before(before.getDateDeactivation().toString())
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        if (before.getDateDeactivation() != null && after.getDateDeactivation() != null
                && !before.getDateActivation().equals(after.getDateActivation())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("date_activation")
                    .before(before.getDateActivation().toString())
                    .after(after.getDateActivation().toString())
                    .build());
        }

        if (!before.getLastUpdate().equals(after.getLastUpdate())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("last_update")
                    .before(before.getLastUpdate().toString())
                    .after(after.getLastUpdate().toString())
                    .build());
        }

        if (!before.getIsArchive().equals(after.getIsArchive())) {
            beforeAfterSources.add(BeforeAfterPattern
                    .builder()
                    .patternLoggerId(loggerId)
                    .columnName("archive")
                    .before(before.getIsArchive().toString())
                    .after(after.getIsArchive().toString())
                    .build());
        }
        beforeAfterPatternRepo.saveAll(beforeAfterSources);
    }

}
