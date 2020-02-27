package ru.iac.ASGIHDTORIS.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreation;

    private long sourceId;

}
