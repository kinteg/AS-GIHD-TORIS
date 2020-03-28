package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SourceDateModel {

    private LocalDate dateCreation1;
    private LocalDate dateCreation2;

    private LocalDate dateDeactivation1;
    private LocalDate dateDeactivation2;

    private LocalDate dateActivation1;
    private LocalDate dateActivation2;

    private LocalDate lastUpdate1;
    private LocalDate lastUpdate2;

}
