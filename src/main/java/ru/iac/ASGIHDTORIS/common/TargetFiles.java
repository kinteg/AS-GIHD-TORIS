package ru.iac.ASGIHDTORIS.common;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TargetFiles {

    private static List<String> fileList = new ArrayList<>();
    private static List<String> archiveList = new ArrayList<>();

    static {
        fileList.add("csv");
        fileList.add("txt");
        fileList.add("xlsx");
    }

    static {
        archiveList.add("zip");
        archiveList.add("7z");
        archiveList.add("tar");
    }

    public static boolean isTargetFile(String filename) {

        for (String target :
                fileList) {

            if (FilenameUtils.getExtension(filename)
                    .equals(target)) {

                return true;
            }

        }

        return false;
    }

    public static boolean isArchive(String filename) {
        for (String target :
                archiveList) {

            if (FilenameUtils.getExtension(filename)
                    .equals(target)) {

                return true;
            }

        }

        return false;
    }

}
