package ru.iac.ASGIHDTORIS.lib.lib.common.target.archive;

import lombok.Getter;

@Getter
public enum ArchiveExtension {

    ZIP("zip"),
    SEVEN_Z("7z"),
    TAR("tar"),
    NON_TARGET("non target");

    private String extension;

    ArchiveExtension(String extension) {
        this.extension = extension;
    }
}
