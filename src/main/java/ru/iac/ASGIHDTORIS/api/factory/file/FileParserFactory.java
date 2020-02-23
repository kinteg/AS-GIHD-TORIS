package ru.iac.ASGIHDTORIS.api.factory.file;

import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;
import ru.iac.ASGIHDTORIS.api.parser.json.csv.CsvParser;

public class FileParserFactory {

    private FileParserFactory() {}

    public static FileParser getParser(String fileName) {
        return changeParser(FilenameUtils.getExtension(fileName));
    }

    private static FileParser changeParser(String extension) {

        switch (extension) {
            case "csv":
                return new CsvParser();
            case "xml":

            case "txt":

            case "json":

            default:
                return null;
        }

    }

}
