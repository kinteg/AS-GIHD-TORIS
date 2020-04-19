package ru.iac.ASGIHDTORIS.common.factory;

import ru.iac.ASGIHDTORIS.parser.file.parser.FileParser;
import ru.iac.ASGIHDTORIS.parser.file.parser.impl.CsvParser;
import ru.iac.ASGIHDTORIS.parser.file.parser.impl.FileParserImpl;
import ru.iac.ASGIHDTORIS.parser.file.parser.impl.TxtParser;
import ru.iac.ASGIHDTORIS.parser.file.parser.impl.XlsxParser;

public final class FileParserFactory {

    private FileParserFactory() {
    }

    public static FileParser getParser(String extension) {

        switch (extension.toLowerCase()) {
            case "txt":
                return new TxtParser();
            case "csv":
                return new CsvParser();
            case "xlsx":
                return new XlsxParser();

            default:
                return new FileParserImpl();
        }

    }

}
