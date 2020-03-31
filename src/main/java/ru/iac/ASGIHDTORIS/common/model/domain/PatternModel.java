package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PatternModel {

    private String id;
    private String name;
    private String description;
    private String direction;
    private String management;

    private Long sourceId;

    private HelpModel helpModel;

    private Integer fileCount1;
    private Integer fileCount2;

    private Integer archiveFileCount1;
    private Integer archiveFileCount2;

}
