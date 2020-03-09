package ru.iac.ASGIHDTORIS.api.parser.json.reader;

public interface Reader extends AutoCloseable {

    String readLine() throws Exception;

    String[] readNext() throws Exception;

}
