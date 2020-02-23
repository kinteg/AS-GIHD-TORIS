package ru.iac.ASGIHDTORIS.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class PatternTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nameTable;
    private String nameFile;
    private long patternId;
}
