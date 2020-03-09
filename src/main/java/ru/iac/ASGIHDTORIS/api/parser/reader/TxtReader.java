package ru.iac.ASGIHDTORIS.api.parser.reader;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TxtReader implements FileReader {

    private BufferedReader reader;
    private final List<List<String>> allList;
    private final List<String> firstRecord;
    private int nextRecord = -1;

    public TxtReader(File file) throws IOException {
        this(Files.newBufferedReader(Paths.get(file.getName()), Charset.forName("windows-1251")));
    }

    public TxtReader(BufferedReader reader) {
        this.reader = reader;
        allList = new ArrayList<>();
        List<String> list = reader.lines().collect(Collectors.toList());

        for (String str :
                list) {
            allList.add(Arrays.asList(str.split(">")));
        }

        firstRecord = allList.get(0);
        log.info(firstRecord.size() + " " + firstRecord.get(0));
    }

    @Override
    public List<List<String>> getAll() {
        return allList;
    }

    @Override
    public List<String> getNextRecord() {
        return ++nextRecord < allList.size() ?
                allList.get(nextRecord) :
                Collections.emptyList();
    }

    @Override
    public List<String> getFirst() {
        return firstRecord;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }

}
