package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(id, source.id) &&
                Objects.equals(name, source.name) &&
                Objects.equals(longName, source.longName) &&
                Objects.equals(shortName, source.shortName) &&
                Objects.equals(description, source.description) &&
                Objects.equals(addDescription, source.addDescription) &&
                Objects.equals(scope, source.scope) &&
                Objects.equals(periodicity, source.periodicity) &&
                Objects.equals(renewalPeriod, source.renewalPeriod) &&
                Objects.equals(type, source.type) &&
                Objects.equals(tags, source.tags) &&
                Objects.equals(providerLink, source.providerLink) &&
                Objects.equals(dataSource, source.dataSource) &&
                Objects.equals(isArchive, source.isArchive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, longName, shortName, description, addDescription, scope, periodicity, renewalPeriod, type, tags, providerLink, dataSource, isArchive);
    }
}
