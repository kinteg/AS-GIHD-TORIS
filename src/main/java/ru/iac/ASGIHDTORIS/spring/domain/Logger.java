package ru.iac.ASGIHDTORIS.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String action;
    private String status;
    private String error;

    private String object;

}
