package ru.iac.ASGIHDTORIS.common.factory;

import com.opencsv.CSVReader;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import ru.iac.ASGIHDTORIS.parser.file.reader.BufferReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.reader.CsvReaderImpl;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;
import ru.iac.ASGIHDTORIS.parser.file.reader.XlsxReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ReaderFactory {

    private ReaderFactory() {
    }

    public static Reader getReader(File file) throws IOException {

        switch (FileNameUtils.getExtension(file.getName()).toLowerCase()) {
            case "txt":
                return new BufferReaderImpl(Files.newBufferedReader(Paths.get(file.getAbsolutePath()), Charset.forName("windows-1251")));
            case "csv":
                return new CsvReaderImpl(new CSVReader(new java.io.FileReader(file)));
            case "xlsx":
                return new XlsxReader(WorkbookFactory.create(file));
            default:
                return null;
        }

    }
}
