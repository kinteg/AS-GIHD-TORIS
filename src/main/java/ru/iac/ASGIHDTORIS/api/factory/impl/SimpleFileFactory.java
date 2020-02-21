package ru.iac.ASGIHDTORIS.api.factory.impl;

import ru.iac.ASGIHDTORIS.api.factory.ParserFactory;
import ru.iac.ASGIHDTORIS.api.parser.Parser;
import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.parser.csv.CsvIntoJson;

public class SimpleFileFactory implements ParserFactory {

    public static Parser getParser(String filename) {
        Parser parser;

        switch (FilenameUtils.getExtension(filename)) {
            case "csv":
                parser = new CsvIntoJson();
                break;

            case "xml":

            case "txt":

            case "json":

            default:
                parser = null;
        }

        return parser;
    }

}
