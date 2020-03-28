package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private LocalDate dateCreation1;
    private LocalDate dateCreation2;

    private LocalDate dateDeactivation1;
    private LocalDate dateDeactivation2;

    private LocalDate dateActivation1;
    private LocalDate dateActivation2;

    private LocalDate lastUpdate1;
    private LocalDate lastUpdate2;

    private String sort;
    private String key;

    private Boolean isArchive;

}
