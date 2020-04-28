package ru.iac.ASGIHDTORIS.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModelPage;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTable;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class PatternTableControllerAspect {

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.createPatternTable(..))")
    public void callCreatePatternTable() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getTable(..))")
    public void callGetTable() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.existByName(..))")
    public void callExistByName() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getById(..))")
    public void callGetById() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAll(..))")
    public void callGetAll() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllSort(..))")
    public void callGetAllSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllByPatternId(..))")
    public void callGetAllByPatternId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllBySourceId(..))")
    public void callGetAllBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllByPatternIdSort(..))")
    public void callGetAllByPatternIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllBySourceIdSort(..))")
    public void callGetAllBySourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchive(..))")
    public void callGetAllArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchiveSort(..))")
    public void callGetAllArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchiveByPatternId(..))")
    public void callGetAllArchiveByPatternId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchiveByPatternIdSort(..))")
    public void callGetAllArchiveByPatternIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchiveBySourceId(..))")
    public void callGetArchiveBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllArchiveBySourceIdSort(..))")
    public void callGetAllArchiveBySourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchive(..))")
    public void callGetAllNotArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchiveSort(..))")
    public void callGetAllNotArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchiveByPatternId(..))")
    public void callGetAllNotArchiveByPatternId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchiveByPatternIdSort(..))")
    public void callGetAllNotArchiveByPatternIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchiveBySourceId(..))")
    public void callGetAllNotArchiveBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.getAllNotArchiveBySourceIdSort(..))")
    public void callGetAllNotArchiveBySourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.archivePattern(..))")
    public void callArchivePattern() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.deArchivePattern(..))")
    public void callDeArchivePattern() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.archivePatterns(..))")
    public void callArchivePatterns() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.deArchivePatterns(..))")
    public void callDeArchivePatterns() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.archivePatternsBySource(..))")
    public void callArchivePatternsBySource() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternTableController.deArchivePatternsBySource(..))")
    public void callDeArchivePatternsBySource() {
    }

    @Before("callCreatePatternTable()")
    public void beforeCallAtCreatePatternTable(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callCreatePatternTable()", returning = "patternTable")
    public void afterReturningCallAtCreatePatternTable(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callGetTable()")
    public void beforeCallAtGetTable(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetTable()", returning = "fullTableModelPage")
    public void afterReturningCallAtGetTable(JoinPoint jp, FullTableModelPage fullTableModelPage) {
//        log.info(
//                "afterReturning " + jp.toString() + ", return=[\n" +
//                        "   filename=" + fullTableModelPage.getTableModel().getFilename() + ",\n" +
//                        "   tableName=" + fullTableModelPage.getTableModel().getTableName() + ",\n" +
//                        "   models=[\n      " + fullTableModelPage
//                        .getTableModel()
//                        .getModels()
//                        .stream()
//                        .map(DataModel::toString).collect(Collectors.joining("\n       ")) +
//                        "]" + ",\n" +
//                        "   values=(\n" +
//                        "   pageCount=" + fullTableModelPage.getValues().getTotalPages() + ",\n" +
//                        "   totalElements=" + fullTableModelPage.getValues().getTotalElements() + ",\n" +
//                        "   pageNumber=" + fullTableModelPage.getValues().getPageable().getPageNumber() + ",\n" +
//                        "   pageSize=" + fullTableModelPage.getValues().getPageable().getPageSize() + ",\n" +
//                        "   sort=" + fullTableModelPage.getValues().getPageable().getSort() + ",\n" +
//                        "   values=[\n" +
//                        "       " +
//                        fullTableModelPage.getValues().getContent()
//                                .stream()
//                                .map(Map::entrySet)
//                                .map(entries -> entries.stream()
//                                        .map(v -> v.getKey() + "=" + v.getValue())
//                                        .collect(Collectors.joining(",\n         "))
//                                )
//                                .collect(Collectors.joining("\n       ")) +
//                        "\n" +
//                        "       ]\n" +
//                        "]"
//        );
    }

    @Before("callExistByName()")
    public void beforeCallAtExistByName(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callExistByName()", returning = "exist")
    public void afterReturningCallAtExistByName(JoinPoint jp, boolean exist) {
        log.info("after " + jp.toString() + ", exist=" + exist);
    }

    @Before("callGetById()")
    public void beforeCallAtGetById(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetById()", returning = "patternTable")
    public void afterReturningCallAtGetById(JoinPoint jp, PatternTable patternTable) {
        log.info("after " + jp.toString() + ", patternTable=" + patternTable);
    }

    @Before("callGetAll()")
    public void beforeCallAtGetAll(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAll()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAll(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllSort()")
    public void beforeCallAtGetAllSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllByPatternId()")
    public void beforeCallAtGetAllByPatternId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllByPatternId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllByPatternId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllBySourceId()")
    public void beforeCallAtGetAllBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllBySourceId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllBySourceId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllByPatternIdSort()")
    public void beforeCallAtPatternIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllByPatternIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtPatternIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllBySourceIdSort()")
    public void beforeCallAtGetAllBySourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllBySourceIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllBySourceIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllArchive()")
    public void beforeCallAtGetAllArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchive()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllArchive(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllArchiveSort()")
    public void beforeCallAtGetAllArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllArchiveSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllArchiveByPatternId()")
    public void beforeCallAtGetAllArchiveByPatternId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveByPatternId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllArchiveByPatternId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllArchiveByPatternIdSort()")
    public void beforeCallAtGetAllArchiveByPatternIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveByPatternIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllArchiveByPatternIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetArchiveBySourceId()")
    public void beforeCallAtGetArchiveBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetArchiveBySourceId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetArchiveBySourceId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllArchiveBySourceIdSort()")
    public void beforeCallAtGetAllArchiveBySourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveBySourceIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllArchiveBySourceIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchive()")
    public void beforeCallAtGetAllNotArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchive()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchive(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchiveSort()")
    public void beforeCallAtGetAllNotArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchiveSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchiveByPatternId()")
    public void beforeCallAtGetAllNotArchiveByPatternId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveByPatternId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchiveByPatternId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchiveByPatternIdSort()")
    public void beforeCallAtGetAllNotArchiveByPatternIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveByPatternIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchiveByPatternIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchiveBySourceId()")
    public void beforeCallAtGetAllNotArchiveBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveBySourceId()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchiveBySourceId(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callGetAllNotArchiveBySourceIdSort()")
    public void beforeCallAtGetAllNotArchiveBySourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveBySourceIdSort()", returning = "pagePatternTable")
    public void afterReturningCallAtGetAllNotArchiveBySourceIdSort(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        printPagePattern(jp, pagePatternTable);
    }

    @Before("callArchivePattern()")
    public void beforeCallAtArchivePattern(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchivePattern()", returning = "patternTable")
    public void afterReturningCallAtArchivePattern(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callDeArchivePattern()")
    public void beforeCallAtDeArchivePattern(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchivePattern()", returning = "patternTable")
    public void afterReturningCallAtDeArchivePattern(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callArchivePatterns()")
    public void beforeCallAtArchivePatterns(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchivePatterns()", returning = "patternTable")
    public void afterReturningCallAtArchivePatterns(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callDeArchivePatterns()")
    public void beforeCallAtDeArchivePatterns(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchivePatterns()", returning = "patternTable")
    public void afterReturningCallAtDeArchivePatterns(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callArchivePatternsBySource()")
    public void beforeCallAtArchivePatternsBySource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchivePatternsBySource()", returning = "patternTable")
    public void afterReturningCallAtArchivePatternsBySource(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    @Before("callDeArchivePatternsBySource()")
    public void beforeCallAtDeArchivePatternsBySource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchivePatternsBySource()", returning = "patternTable")
    public void afterReturningCallAtDeArchivePatternsBySource(JoinPoint jp, PatternTable patternTable) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTable.toString());
    }

    private void printPagePattern(JoinPoint jp, Page<PatternTable> pagePatternTable) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                "return=[\n" +
                "   pageCount=" + pagePatternTable.getTotalPages() + ",\n" +
                "   totalElements=" + pagePatternTable.getTotalElements() + ",\n" +
                "   pageNumber=" + pagePatternTable.getPageable().getPageNumber() + ",\n" +
                "   pageSize=" + pagePatternTable.getPageable().getPageSize() + ",\n" +
                "   sort=" + pagePatternTable.getPageable().getSort() + ",\n" +
                "   values=[\n" +
                "       " +
                pagePatternTable.getContent()
                        .stream()
                        .map(PatternTable::toString)
                        .collect(Collectors.joining("\n       ")) +
                "\n" +
                "       ]\n" +
                "]");
    }

}
