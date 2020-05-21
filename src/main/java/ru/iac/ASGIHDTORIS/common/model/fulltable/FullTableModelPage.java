package ru.iac.ASGIHDTORIS.common.model.fulltable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import ru.iac.ASGIHDTORIS.lib.lib.common.model.TableModel;

import java.util.Map;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class FullTableModelPage {

    private TableModel tableModel;
    private Page<Map<String, String>> values;

    public static FullTableModelPage getEmptyFullTableModelPage() {
        return FullTableModelPage
                .builder()
                .tableModel(TableModel.emptyTableModel())
                .values(Page.empty())
                .build();
    }

}
