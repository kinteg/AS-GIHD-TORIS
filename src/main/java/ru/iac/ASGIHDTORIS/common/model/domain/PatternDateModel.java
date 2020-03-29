package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PatternDateModel {

    private Long id;
    private Integer fileCount;
    private String name;
    private String description;
    private String direction;
    private String management;

    private Boolean isArchive;

    private Long sourceId;

    private LocalDateTime dateCreation1;
    private LocalDateTime dateCreation2;

    private LocalDateTime dateDeactivation1;
    private LocalDateTime dateDeactivation2;

    private LocalDateTime dateActivation1;
    private LocalDateTime dateActivation2;

    private LocalDateTime lastUpdate1;
    private LocalDateTime lastUpdate2;

    private Integer fileCount1;
    private Integer fileCount2;

}
