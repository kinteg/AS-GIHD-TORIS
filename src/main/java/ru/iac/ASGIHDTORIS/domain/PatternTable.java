package ru.iac.ASGIHDTORIS.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Data
public class PatternTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nameTable;
    private String nameFile;
    private long patternId;

    public PatternTable() {
    }

    public PatternTable(String nameFile) {
        this.nameFile = nameFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternTable that = (PatternTable) o;
        return nameFile.equals(that.nameFile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameFile);
    }
}
