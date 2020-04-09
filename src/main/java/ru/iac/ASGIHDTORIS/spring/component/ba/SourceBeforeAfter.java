package ru.iac.ASGIHDTORIS.spring.component.ba;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterSource;
import ru.iac.ASGIHDTORIS.spring.domain.Source;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterSourceRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class SourceBeforeAfter implements BeforeAfter<Source> {

    private final BeforeAfterSourceRepo beforeAfterSourceRepo;

    public SourceBeforeAfter(BeforeAfterSourceRepo beforeAfterSourceRepo) {
        this.beforeAfterSourceRepo = beforeAfterSourceRepo;
    }

    @Override
    public void afterCreate(Source after, Long loggerId) {
        List<BeforeAfterSource> beforeAfterSources = new ArrayList<>();

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getId().toString())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getName())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getLongName())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getShortName())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getDescription())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getAddDescription())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getScope())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getPeriodicity())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getRenewalPeriod())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getType())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getTags())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getProviderLink())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getDataSource())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getDateCreation().toString())
                .build());

        if (after.getDateDeactivation() != null) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(null)
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getDateActivation().toString())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getLastUpdate().toString())
                .build());

        beforeAfterSources.add(BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(null)
                .after(after.getIsArchive().toString())
                .build());

        beforeAfterSourceRepo.saveAll(beforeAfterSources);
    }

    @Override
    public void afterArchive(Source before, Source after, Long loggerId) {
        BeforeAfterSource beforeAfterSource = BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterSourceRepo.save(beforeAfterSource);

        beforeAfterSource = BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(before.getDateDeactivation() == null ?
                        null : before.getDateDeactivation().toString())
                .after(after.getDateDeactivation().toString())
                .build();

        beforeAfterSourceRepo.save(beforeAfterSource);
    }

    @Override
    public void afterDeArchive(Source before, Source after, Long loggerId) {
        BeforeAfterSource beforeAfterSource = BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterSourceRepo.save(beforeAfterSource);

        beforeAfterSource = BeforeAfterSource
                .builder()
                .sourceLoggerId(loggerId)
                .before(before.getDateActivation().toString())
                .after(after.getDateActivation().toString())
                .build();

        beforeAfterSourceRepo.save(beforeAfterSource);
    }

    @Override
    public void afterUpdate(Source before, Source after, Long loggerId) {
        List<BeforeAfterSource> beforeAfterSources = new ArrayList<>();

        if (!before.getId().equals(after.getId())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getId().toString())
                    .after(after.getId().toString())
                    .build());
        }

        if (!before.getName().equals(after.getName())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getName())
                    .after(after.getName())
                    .build());
        }

        if (!before.getLongName().equals(after.getLongName())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getLongName())
                    .after(after.getLongName())
                    .build());
        }

        if (!before.getShortName().equals(after.getShortName())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getShortName())
                    .after(after.getShortName())
                    .build());
        }

        if (!before.getDescription().equals(after.getDescription())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getDescription())
                    .after(after.getDescription())
                    .build());
        }

        if (!before.getAddDescription().equals(after.getAddDescription())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getAddDescription())
                    .after(after.getAddDescription())
                    .build());
        }

        if (!before.getScope().equals(after.getScope())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getScope())
                    .after(after.getScope())
                    .build());
        }

        if (!before.getPeriodicity().equals(after.getPeriodicity())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getPeriodicity())
                    .after(after.getPeriodicity())
                    .build());
        }

        if (!before.getRenewalPeriod().equals(after.getRenewalPeriod())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getRenewalPeriod())
                    .after(after.getRenewalPeriod())
                    .build());
        }

        if (!before.getType().equals(after.getType())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getType())
                    .after(after.getType())
                    .build());
        }

        if (!before.getTags().equals(after.getTags())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getTags())
                    .after(after.getTags())
                    .build());
        }

        if (!before.getProviderLink().equals(after.getProviderLink())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getProviderLink())
                    .after(after.getProviderLink())
                    .build());
        }

        if (!before.getDataSource().equals(after.getDataSource())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getDataSource())
                    .after(after.getDataSource())
                    .build());
        }

        if (!before.getDateCreation().equals(after.getDateCreation())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getDateCreation().toString())
                    .after(after.getDateCreation().toString())
                    .build());
        }

        if (before.getDateDeactivation() != null && after.getDateDeactivation() != null
                && !before.getDateDeactivation().equals(after.getDateDeactivation())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getDateDeactivation().toString())
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        if (!before.getDateActivation().equals(after.getDateActivation())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getDateActivation().toString())
                    .after(after.getDateActivation().toString())
                    .build());
        }

        if (!before.getLastUpdate().equals(after.getLastUpdate())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getLastUpdate().toString())
                    .after(after.getLastUpdate().toString())
                    .build());
        }

        if (!before.getIsArchive().equals(after.getIsArchive())) {
            beforeAfterSources.add(BeforeAfterSource
                    .builder()
                    .sourceLoggerId(loggerId)
                    .before(before.getIsArchive().toString())
                    .after(after.getIsArchive().toString())
                    .build());
        }
        beforeAfterSourceRepo.saveAll(beforeAfterSources);
    }

}
