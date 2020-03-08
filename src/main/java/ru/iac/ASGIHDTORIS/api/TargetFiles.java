package ru.iac.ASGIHDTORIS.api;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TargetFiles {

    private List<String> targetList = new ArrayList<>();
    private List<String> archiveList = new ArrayList<>();

    {
        targetList.add("csv");
        targetList.add("txt");
    }

    {
        archiveList.add("zip");
        archiveList.add("7z");
    }

    public boolean isTargetFile(String filename) {

        for (String target :
                targetList) {

            if (FilenameUtils.getExtension(filename)
                    .equals(target) ) {

                return true;
            }

        }

        return false;
    }

    public boolean isArchive(String filename) {
        for (String target :
                archiveList) {

            if (FilenameUtils.getExtension(filename)
                    .equals(target) ) {

                return true;
            }

        }

        return false;
    }

}
