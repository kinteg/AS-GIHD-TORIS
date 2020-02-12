package ru.iac.ASGIHDTORIS.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;

}
