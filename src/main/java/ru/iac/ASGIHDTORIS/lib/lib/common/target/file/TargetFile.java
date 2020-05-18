package ru.iac.ASGIHDTORIS.lib.lib.common.target.file;

public interface TargetFile {

    boolean isTargetFile(String filename);

    FileExtension getExtension(String filename);

}
