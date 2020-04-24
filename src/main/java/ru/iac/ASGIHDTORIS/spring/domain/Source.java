package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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

    @NotEmpty
    @NotNull
    private String name;
    @NotEmpty
    @NotNull
    private String longName;
    @NotEmpty
    @NotNull
    private String shortName;

    @NotEmpty
    @NotNull
    private String description;
    @NotEmpty
    @NotNull
    private String addDescription;

    @NotEmpty
    @NotNull
    private String scope;
    @NotEmpty
    @NotNull
    private String periodicity;
    @NotEmpty
    @NotNull
    private String renewalPeriod;
    @NotEmpty
    @NotNull
    private String type;
    @NotEmpty
    @NotNull
    private String tags;
    @NotEmpty
    @NotNull
    private String providerLink;
    @NotEmpty
    @NotNull
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

    public void setCreateTime() {
        this.setDateCreation(LocalDateTime.now());
        this.setDateActivation(LocalDateTime.now());
        this.setLastUpdate(LocalDateTime.now());
    }

    public void setUpdateTime(Source beforeUpdate) {
        this.setLastUpdate(LocalDateTime.now());
        this.setDateCreation(beforeUpdate.getDateCreation());
        this.setDateActivation(beforeUpdate.getDateActivation());
        this.setDateDeactivation(beforeUpdate.getDateDeactivation());
    }

    public void archive() {
        this.setIsArchive(true);
        this.setDateDeactivation(LocalDateTime.now());
    }

    public void deArchive() {
        this.setIsArchive(false);
        this.setDateActivation(LocalDateTime.now());
    }

    public static Source getArchiveInfo(Source sourceAfter) {
        return Source
                .builder()
                .isArchive(sourceAfter.getIsArchive())
                .dateDeactivation(sourceAfter.getDateDeactivation())
                .build();
    }

    public static Source getDeArchiveInfo(Source sourceAfter) {
        return Source
                .builder()
                .isArchive(sourceAfter.getIsArchive())
                .dateActivation(sourceAfter.getDateActivation())
                .build();
    }

    public static Source getBadIdSource(long id) {
        return Source.builder().id(id).build();
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
