package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Source {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String longName;
    private String shortName;

    private String description;
    private String addDescription;

    private String scope;
    private String periodicity;
    private String renewalPeriod;
    private String type;
    private String tags;
    private String providerLink;
    private String dataSource;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDeactivation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateActivation;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastUpdate;

    @Column(name = "archive")
    private Boolean isArchive;

    public Source(Source source) {
        id = source.getId();
        name = source.getName();
        longName = source.getLongName();
        shortName = source.getShortName();
        description = source.getDescription();
        addDescription = source.getAddDescription();
        scope = source.getScope();
        periodicity = source.getPeriodicity();
        renewalPeriod = source.getRenewalPeriod();
        type = source.getType();
        tags = source.getTags();
        providerLink = source.getProviderLink();
        dataSource = source.getDataSource();
        dateCreation = source.getDateCreation();
        dateDeactivation = source.getDateDeactivation();
        dateActivation = source.getDateActivation();
        lastUpdate = source.getLastUpdate();
        isArchive = source.getIsArchive();
    }
}
