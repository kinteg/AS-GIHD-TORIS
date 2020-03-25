package ru.iac.ASGIHDTORIS.common.factory;

import ru.iac.ASGIHDTORIS.parser.file.parser.CsvParser;
import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.parser.TxtParser;

public final class FileParserFactory {

    private FileParserFactory() {}


    public static FileParser getParser(String extension) {

        switch (extension.toLowerCase()) {
            case "txt":
                return new TxtParser();
            case "csv":
                return new CsvParser();

            default:
                return null;
        }

    }

}
