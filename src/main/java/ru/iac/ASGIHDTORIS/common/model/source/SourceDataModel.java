package ru.iac.ASGIHDTORIS.common.model.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SourceDataModel {

    private LocalDateTime dateCreation1;
    private LocalDateTime dateCreation2;

    private LocalDateTime dateDeactivation1;
    private LocalDateTime dateDeactivation2;

    private LocalDateTime dateActivation1;
    private LocalDateTime dateActivation2;

    private LocalDateTime lastUpdate1;
    private LocalDateTime lastUpdate2;

}
