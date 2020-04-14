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
public class PatternTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameTable;
    private String nameFile;
    private Long patternId;
    private Long sourceId;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateKill;

    private Long version;

    @Column(name = "active")
    private Boolean isActive;

    public PatternTable(PatternTable patternTable) {
        this.id = patternTable.getId();
        this.nameTable = patternTable.getNameTable();
        this.nameFile = patternTable.getNameFile();
        this.patternId = patternTable.getPatternId();
        this.sourceId = patternTable.getSourceId();
        this.isArchive = patternTable.getIsArchive();
        this.dateCreation = patternTable.getDateCreation();
        this.dateDeactivation = patternTable.getDateDeactivation();
        this.dateActivation = patternTable.getDateActivation();
        this.lastUpdate = patternTable.getLastUpdate();
        this.dateKill = patternTable.getDateKill();
        this.version = patternTable.getVersion();
        this.isActive = patternTable.getIsActive();
    }
}
