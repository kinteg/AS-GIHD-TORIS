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
public class SourceLogger {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "action_id", referencedColumnName = "id")
    private Actions actions;

    @ManyToOne
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Statuses statuses;

    @ManyToOne
    @JoinColumn(name = "error_id", referencedColumnName = "id")
    private Errors errors;

    private Long sourceId;

    private LocalDateTime dateCreation;

}
