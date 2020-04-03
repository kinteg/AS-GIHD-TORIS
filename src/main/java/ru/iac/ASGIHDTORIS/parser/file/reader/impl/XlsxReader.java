package ru.iac.ASGIHDTORIS.parser.file.reader.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.iac.ASGIHDTORIS.parser.file.reader.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public class XlsxReader implements Reader {

    private final Workbook reader;
    private final Sheet sheet;
    private final int count;
    private int line;

    public XlsxReader(Workbook reader) {
        this.reader = reader;
        sheet = reader.getSheetAt(0);
        count = sheet.getLastRowNum();
        line = 0;
    }

    @Override
    public String readLine() {
        return readNext().toString();
    }

    @Override
    public List<String> readNext() {

        if (line > count) {
            return Collections.emptyList();
        }

        return cells();
    }

    private List<String> cells() {
        Row row = sheet.getRow(line);
        List<String> cells = new ArrayList<>();

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            cells.add(cell.getStringCellValue());
        }

        return cells;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
