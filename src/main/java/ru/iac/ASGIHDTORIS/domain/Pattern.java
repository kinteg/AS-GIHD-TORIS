package ru.iac.ASGIHDTORIS.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String fileCount;
    private String description;
    private String direction;
    private String management;



    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "source_id")
    private Source source;
}
