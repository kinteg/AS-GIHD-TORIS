package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PatternTableLogger {

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

    private Long patternTableId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dateCreation;

    @OneToOne
    @JoinColumn(name = "usr_id", referencedColumnName = "id")
    private User usrId;

}
