package ru.iac.ASGIHDTORIS.common.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class PatternTableModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String nameTable;
    private String nameFile;
    private Long patternId;
    private Long sourceId;
    private Boolean isActive;

    private HelpModel helpModel;

}
