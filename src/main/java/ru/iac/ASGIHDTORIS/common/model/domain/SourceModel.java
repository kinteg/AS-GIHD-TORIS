package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private HelpModel helpModel;


}
