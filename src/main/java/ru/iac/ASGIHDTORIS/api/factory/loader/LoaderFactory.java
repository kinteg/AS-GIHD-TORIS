package ru.iac.ASGIHDTORIS.api.factory.loader;

import org.apache.commons.io.FilenameUtils;
import ru.iac.ASGIHDTORIS.api.db.loader.CSVLoader;
import ru.iac.ASGIHDTORIS.api.db.loader.Loader;

import java.sql.Connection;

public final class LoaderFactory {

    private LoaderFactory() {}

    public static Loader getParser(String fileName, Connection connection) {
        return changeParser(FilenameUtils.getExtension(fileName), connection);
    }

    private static Loader changeParser(String extension, Connection connection) {

        switch (extension) {
            case "csv":
                return new CSVLoader(connection);
            case "xml":

            case "txt":

            case "json":

            default:
                return null;
        }

    }

}
