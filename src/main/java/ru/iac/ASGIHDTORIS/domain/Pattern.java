package ru.iac.ASGIHDTORIS.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fileCount;
    private String name;
    private String description;
    private String direction;
    private String management;
    private LocalDateTime dateCreation;

    private long sourceId;

}
