package ru.iac.ASGIHDTORIS.common.model.table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PatternTableModelStatus {

    private TableModelStatus tableModel;
    private PatternTable patternTable;

}
