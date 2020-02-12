package ru.iac.ASGIHDTORIS.api.factory.impl;

import ru.iac.ASGIHDTORIS.api.factory.ParserFactory;
import ru.iac.ASGIHDTORIS.parser.Parser;
import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.parser.csv.impl.CsvParserImpl;


public class ParserFactoryImpl implements ParserFactory {


    public static Parser getParser(String filename) {
        Parser parser;

        switch (FilenameUtils.getExtension(filename)) {
            case "csv":
                parser = getCsvParser();
                break;

            case "xml":

            case "txt":

            case "json":

            default:
                parser = null;
        }

        return parser;
    }

    private static Parser getCsvParser() {
        return new CsvParserImpl();
    }
}
