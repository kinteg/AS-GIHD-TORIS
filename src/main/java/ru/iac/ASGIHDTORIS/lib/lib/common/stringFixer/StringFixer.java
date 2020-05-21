package ru.iac.ASGIHDTORIS.lib.lib.common.stringFixer;

import java.util.List;
import java.util.stream.Collectors;

public class StringFixer {

    public static List<String> fixStrings(List<String> strings) {
        return strings.stream().map(StringFixer::fixString).collect(Collectors.toList());
    }

    public static String fixString(String string) {
        return string.replaceAll("\uFEFF", "").trim();
    }

}
