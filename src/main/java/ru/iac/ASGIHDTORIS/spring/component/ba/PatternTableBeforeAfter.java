package ru.iac.ASGIHDTORIS.spring.component.ba;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPattern;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternRepo;
import ru.iac.ASGIHDTORIS.spring.repo.BeforeAfterPatternTableRepo;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatternTableBeforeAfter implements BeforeAfter<PatternTable> {

    private final BeforeAfterPatternTableRepo beforeAfterPatternTableRepo;

    public PatternTableBeforeAfter(BeforeAfterPatternTableRepo beforeAfterPatternTableRepo) {
        this.beforeAfterPatternTableRepo = beforeAfterPatternTableRepo;
    }

    @Override
    public void afterCreate(PatternTable after, Long loggerId) {
        List<BeforeAfterPatternTable> beforeAfterPatternTables = new ArrayList<>();

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getNameTable())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getNameFile())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getPatternId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getSourceId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getDateCreation().toString())
                .build());

        if (after.getDateDeactivation() != null) {
            beforeAfterPatternTables.add(BeforeAfterPatternTable
                    .builder()
                    .patternTableLoggerId(loggerId)
                    .before(null)
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getDateActivation().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getLastUpdate().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(null)
                .after(after.getIsArchive().toString())
                .build());

        beforeAfterPatternTableRepo.saveAll(beforeAfterPatternTables);
    }

    @Override
    public void afterArchive(PatternTable before, PatternTable after, Long loggerId) {
        BeforeAfterPatternTable beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);

        beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(before.getDateDeactivation() == null ?
                        null : before.getDateDeactivation().toString())
                .after(after.getDateDeactivation().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);
    }

    @Override
    public void afterDeArchive(PatternTable before, PatternTable after, Long loggerId) {
        BeforeAfterPatternTable beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);

        beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .before(before.getDateActivation().toString())
                .after(after.getDateActivation().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);
    }

    @Override
    public void afterUpdate(PatternTable before, PatternTable after, Long loggerId) {

    }
}
