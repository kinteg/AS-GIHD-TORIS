package ru.iac.ASGIHDTORIS.common.model.serch;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SearchModel {

    private String nameColumn;
    private String sort;

    private List<String> keys;
    private List<String> values;

    private Pageable pageable;

}
