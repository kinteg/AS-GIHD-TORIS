package ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import ru.iac.ASGIHDTORIS.lib.lib.parser.file.reader.Reader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XlsxReaderImpl implements Reader {

    private final Workbook reader;
    private final Sheet sheet;
    private final int count;
    private int line;

    public XlsxReaderImpl(Workbook reader) {
        this.reader = reader;
        this.sheet = reader.getSheetAt(0);
        this.count = sheet.getLastRowNum();
        this.line = 0;
    }


    @Override
    public String readLine() {
        return String.join(",", readNext());
    }

    @Override
    public List<String> readNext() {

        if (line > count) {
            return Collections.emptyList();
        }

        return cells();
    }

    private List<String> cells() {
        Row row = sheet.getRow(line++);
        List<String> cells = new ArrayList<>();

        for (int i = 0; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            cells.add(cell.getStringCellValue());
        }

        if (cells.stream().allMatch(v -> v.trim().equals(""))) {
            return readNext();
        }

        return cells;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
