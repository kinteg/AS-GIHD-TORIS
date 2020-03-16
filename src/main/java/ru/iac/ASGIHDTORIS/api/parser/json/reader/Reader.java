package ru.iac.ASGIHDTORIS.api.parser.json.reader;

import java.util.List;

public interface Reader extends AutoCloseable {

    String readLine() throws Exception;

    List<String> readNext() throws Exception;

}
