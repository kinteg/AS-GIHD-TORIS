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
public class Pattern {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer fileCount;
    private Integer archiveFileCount;
    private String name;
    private String description;
    private String direction;
    private String management;

    @Column(name = "archive")
    private Boolean isArchive;

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

    private Long sourceId;

    public Pattern(Pattern pattern) {
        id = pattern.getId();
        name = pattern.getName();
        fileCount = pattern.getFileCount();
        archiveFileCount = pattern.getArchiveFileCount();
        description = pattern.getDescription();
        direction = pattern.getDirection();
        management = pattern.getManagement();
        dateCreation = pattern.getDateCreation();
        dateDeactivation = pattern.getDateDeactivation();
        dateActivation = pattern.getDateActivation();
        lastUpdate = pattern.getLastUpdate();
        isArchive = pattern.getIsArchive();
        sourceId = pattern.getSourceId();
    }

    public void setCreate() {
        this.setDateCreation(LocalDateTime.now());
        this.setDateActivation(LocalDateTime.now());
        this.setLastUpdate(LocalDateTime.now());
        this.setArchiveFileCount(0);
        this.setFileCount(0);
        this.setIsArchive(false);
    }

    public void setUpdate(Pattern beforeUpdate) {
        this.setLastUpdate(LocalDateTime.now());
        this.setDateCreation(beforeUpdate.getDateCreation());
        this.setDateActivation(beforeUpdate.getDateActivation());
        this.setDateDeactivation(beforeUpdate.getDateDeactivation());
    }

    public static Pattern getBadIdPattern(long id) {
        return Pattern.builder().id(id).build();
    }

    public void archive() {
        this.setIsArchive(true);
        this.setDateDeactivation(LocalDateTime.now());
    }

    public void deArchive() {
        this.setIsArchive(false);
        this.setDateActivation(LocalDateTime.now());
    }

    public void incrementFiles(int count) {
        this.setFileCount(this.getFileCount() + count);
        this.setArchiveFileCount(this.getArchiveFileCount() - count);
    }

    public void decrementFiles(int count) {
        this.setFileCount(this.getFileCount() - count);
        this.setArchiveFileCount(this.getArchiveFileCount() + count);
    }

    public static Pattern getArchiveInfo(Pattern patternAfter) {
        return Pattern
                .builder()
                .isArchive(patternAfter.getIsArchive())
                .dateDeactivation(patternAfter.getDateDeactivation())
                .build();
    }

    public static Pattern getDeArchiveInfo(Pattern patternAfter) {
        return Pattern
                .builder()
                .isArchive(patternAfter.getIsArchive())
                .dateActivation(patternAfter.getDateActivation())
                .build();
    }

}


