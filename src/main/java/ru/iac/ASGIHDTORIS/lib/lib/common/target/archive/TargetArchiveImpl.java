package ru.iac.ASGIHDTORIS.lib.lib.common.target.archive;

import org.apache.commons.compress.utils.FileNameUtils;

public class TargetArchiveImpl implements TargetArchive {

    @Override
    public boolean isTargetArchive(String filename) {
        return !getExtension(filename).equals(ArchiveExtension.NON_TARGET);
    }

    @Override
    public ArchiveExtension getExtension(String filename) {
        String extension = getStringExtension(filename);
        try {
            return ArchiveExtension.valueOf(extension);
        } catch (Exception ex) {
            return ArchiveExtension.NON_TARGET;
        }

    }

    private String getStringExtension(String filename) {
        if (filename != null) {
            return getStringExtensionNotNull(filename);
        } else {
            return "";
        }
    }

    private String getStringExtensionNotNull(String filename) {
        filename = FileNameUtils.getExtension(filename).trim().toUpperCase();
        if (isSevenZ(filename)) {
            return "SEVEN_Z";
        } else {
            return filename;
        }
    }

    private boolean isSevenZ(String filename) {
        return filename.equals(ArchiveExtension.SEVEN_Z.getExtension().toUpperCase());
    }

}
