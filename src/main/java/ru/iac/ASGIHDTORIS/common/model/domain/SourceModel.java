package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class SourceModel {

    private String id;

    private String name;
    private String longName;
    private String shortName;

    private String description;
    private String addDescription;

    private String scope;
    private String periodicity;
    private String renewalPeriod;
    private String type;
    private String tags;
    private String providerLink;
    private String dataSource;

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

    private String sort;
    private String key;

    private Boolean isArchive;

}
