package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class HelpModel {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeactivation1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateDeactivation2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateActivation1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateActivation2;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate1;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastUpdate2;

    private Boolean isArchive;

    private String sort;
    private String key;

}
