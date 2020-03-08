package ru.iac.ASGIHDTORIS.api.factory.loader;

import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.parser.reader.CsvReader;
import ru.iac.ASGIHDTORIS.api.parser.reader.FileReader;
import ru.iac.ASGIHDTORIS.api.parser.reader.TxtReader;

import java.io.File;
import java.io.IOException;

public final class LoaderFactory {

    private LoaderFactory() {}

    public static FileReader getParser(File file) throws IOException, CsvException {
        return changeParser(FilenameUtils.getExtension(file.getName()), file);
    }

    private static FileReader changeParser(String extension, File file) throws IOException, CsvException {

        switch (extension.toLowerCase()) {
            case "txt":
                return new TxtReader(file);
            case "csv":
                return new CsvReader(file);
            case "xml":

            case "json":

            default:
                return null;
        }

    }

}
