package ru.iac.ASGIHDTORIS.domain;

import javax.persistence.*;

public class Table {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nameTable;
    private String nameFile;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pattern_id")
    private Pattern pattern_id;
}
