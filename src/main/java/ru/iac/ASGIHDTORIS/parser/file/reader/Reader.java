package ru.iac.ASGIHDTORIS.parser.file.reader;

import java.util.List;

public interface Reader extends AutoCloseable {

    String readLine() throws Exception;

    List<String> readNext() throws Exception;

}
