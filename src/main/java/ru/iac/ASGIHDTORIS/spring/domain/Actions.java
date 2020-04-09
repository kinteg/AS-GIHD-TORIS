package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Actions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String action;

    @JsonIgnore
    @JsonBackReference
    @OneToMany(mappedBy = "actions", fetch = FetchType.EAGER)
    private List<SourceLogger> sourceLogger;

}
