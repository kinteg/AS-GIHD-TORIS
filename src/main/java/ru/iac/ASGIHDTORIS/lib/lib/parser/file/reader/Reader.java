package ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader;

import java.util.List;

public interface Reader extends AutoCloseable {

    String readLine();

    List<String> readNext();

}
