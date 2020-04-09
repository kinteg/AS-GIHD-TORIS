package ru.iac.ASGIHDTORIS.spring.component.ba.impl;

import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.component.ba.BeforeAfter;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterPatternTable;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;
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
                .columnName("id")
                .before("-")
                .after(after.getId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("name_table")
                .before("-")
                .after(after.getNameTable())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("name_file")
                .before("-")
                .after(after.getNameFile())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("pattern_id")
                .before("-")
                .after(after.getPatternId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("source_id")
                .before("-")
                .after(after.getSourceId().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("date_creation")
                .before("-")
                .after(after.getDateCreation().toString())
                .build());

        if (after.getDateDeactivation() != null) {
            beforeAfterPatternTables.add(BeforeAfterPatternTable
                    .builder()
                    .patternTableLoggerId(loggerId)
                    .columnName("date_deactivation")
                    .before("-")
                    .after(after.getDateDeactivation().toString())
                    .build());
        }

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("date_activation")
                .before("-")
                .after(after.getDateActivation().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("last_update")
                .before("-")
                .after(after.getLastUpdate().toString())
                .build());

        beforeAfterPatternTables.add(BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("archive")
                .before("-")
                .after(after.getIsArchive().toString())
                .build());

        beforeAfterPatternTableRepo.saveAll(beforeAfterPatternTables);
    }

    @Override
    public void afterArchive(PatternTable before, PatternTable after, Long loggerId) {
        BeforeAfterPatternTable beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("archive")
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);

        beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("date_deactivation")
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
                .columnName("archive")
                .before(before.getIsArchive().toString())
                .after(after.getIsArchive().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);

        beforeAfterPatternTable = BeforeAfterPatternTable
                .builder()
                .patternTableLoggerId(loggerId)
                .columnName("date_activation")
                .before(before.getDateActivation().toString())
                .after(after.getDateActivation().toString())
                .build();

        beforeAfterPatternTableRepo.save(beforeAfterPatternTable);
    }

    @Override
    public void afterUpdate(PatternTable before, PatternTable after, Long loggerId) {

    }
}
