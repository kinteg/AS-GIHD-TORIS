package ru.iac.ASGIHDTORIS.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Errors {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String error;
    private String errorStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "errors", fetch = FetchType.EAGER)
    private List<SourceLogger> sourceLogger;

}
