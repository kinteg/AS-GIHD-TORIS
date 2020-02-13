package ru.iac.ASGIHDTORIS.api;

import lombok.Getter;
import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TargetFiles {

    private List<String> targetList = new ArrayList<>();

    {
        targetList.add("csv");
    }

    public boolean isTargetFile(String filename) {

        for (String target :
                targetList) {
            if (FilenameUtils
                            .getExtension(filename)
                            .equals(target) ) {
                return true;
            }
        }

        return false;
    }

}
