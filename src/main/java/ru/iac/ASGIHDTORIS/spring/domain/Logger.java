package ru.iac.ASGIHDTORIS.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Logger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Actions actions;

    @OneToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Statuses statuses;

    @OneToOne
    @JoinColumn(name = "error_id", referencedColumnName = "id")
    private Errors errors;

    @OneToOne
    @JoinColumn(name = "object_id", referencedColumnName = "id")
    private Objects objects;

    private LocalDateTime dateCreation;

}
