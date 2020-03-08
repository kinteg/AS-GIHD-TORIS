package ru.iac.ASGIHDTORIS.api.factory.file;

import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.parser.json.FileParser;
import ru.iac.ASGIHDTORIS.api.parser.json.csv.CsvParser;
import ru.iac.ASGIHDTORIS.api.parser.json.txt.TxtParser;

public final class FileParserFactory {

    private FileParserFactory() {}

    public static FileParser getParser(String fileName) {
        return changeParser(FilenameUtils.getExtension(fileName));
    }

    private static FileParser changeParser(String extension) {

        switch (extension.toLowerCase()) {
            case "txt":
                return new TxtParser();
            case "csv":
                return new CsvParser();
            case "xml":

            case "json":

            default:
                return null;
        }

    }

}
