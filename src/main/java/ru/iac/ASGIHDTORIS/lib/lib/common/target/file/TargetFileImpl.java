package ru.iac.ASGIHDTORIS.lib.lib.common.target.file;

import org.apache.commons.compress.utils.FileNameUtils;

public class TargetFileImpl implements TargetFile {

    @Override
    public boolean isTargetFile(String filename) {
        return !getExtension(filename).equals(FileExtension.NON_TARGET);
    }

    @Override
    public FileExtension getExtension(String filename) {
        String extension = getStringExtension(filename);
        try {
            return FileExtension.valueOf(extension);
        } catch (Exception ex) {
            return FileExtension.NON_TARGET;
        }

    }

    private String getStringExtension(String filename) {
        if (filename != null) {
            return FileNameUtils.getExtension(filename).trim().toUpperCase();
        } else {
            return "";
        }
    }

}
