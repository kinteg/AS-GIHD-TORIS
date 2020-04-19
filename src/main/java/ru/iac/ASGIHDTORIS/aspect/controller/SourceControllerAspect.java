package ru.iac.ASGIHDTORIS.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Source;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class SourceControllerAspect {

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.createSource(..))")
    public void callCreateSource() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.checkName(..))")
    public void callCheckName() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getById(..))")
    public void callGetById() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAll(..))")
    public void callGetAll() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAllSort(..))")
    public void callGetAllSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAllArchive(..))")
    public void callGetAllArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAllArchiveSort(..))")
    public void callGetAllArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAllNotArchive(..))")
    public void callGetAllNotArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.getAllNotArchiveSort(..))")
    public void callGetAllNotArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.archiveSource(..))")
    public void callArchiveSource() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.deArchiveSource(..))")
    public void callDeArchiveSource() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.SourceController.updateSource(..))")
    public void callUpdateSource() {
    }

    @Before("callCreateSource()")
    public void beforeCallAtCreateSource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callCreateSource()", returning = "source")
    public void afterReturningCallAtCreateSource(JoinPoint jp, Source source) {
        log.info("afterReturning " + jp.toString() + ", return= " + Objects.toString(source, ""));
    }

    @Before("callCheckName()")
    public void beforeCallAtCheckName(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callCheckName()", returning = "checkName")
    public void afterReturningCallAtCheckName(JoinPoint jp, boolean checkName) {
        log.info("afterReturning " + jp.toString() + ", return= " + checkName);
    }

    @Before("callGetById()")
    public void beforeCallAtGetById(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetById()", returning = "source")
    public void afterReturningCallAtGetById(JoinPoint jp, Source source) {
        log.info("afterReturning " + jp.toString() + ", return= " + source);
    }

    @Before("callGetAll()")
    public void beforeCallAtGetAll(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAll()", returning = "pageSource")
    public void afterReturningCallAtGetAll(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callGetAllSort()")
    public void beforeCallAtGetAllSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllSort()", returning = "pageSource")
    public void afterReturningCallAtGetAllSort(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callGetAllArchive()")
    public void beforeCallAtGetAllArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchive()", returning = "pageSource")
    public void afterReturningCallAtGetAllArchive(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callGetAllArchiveSort()")
    public void beforeCallAtGetAllArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveSort()", returning = "pageSource")
    public void afterReturningCallAtGetAllArchiveSort(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callGetAllNotArchive()")
    public void beforeCallAtGetAllNotArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchive()", returning = "pageSource")
    public void afterReturningCallAtGetAllNotArchive(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callGetAllNotArchiveSort()")
    public void beforeCallAtGetAllNotArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveSort()", returning = "pageSource")
    public void afterReturningCallAtGetAllNotArchiveSort(JoinPoint jp, Page<Source> pageSource) {
        printPageSource(jp, pageSource);
    }

    @Before("callArchiveSource()")
    public void beforeCallAtArchiveSource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchiveSource()", returning = "source")
    public void afterReturningCallAtArchiveSource(JoinPoint jp, Source source) {
        log.info("afterReturning " + jp.toString() + ", return= " + source.toString());
    }

    @Before("callDeArchiveSource()")
    public void beforeCallAtDeArchiveSource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchiveSource()", returning = "source")
    public void afterReturningCallAtDeArchiveSource(JoinPoint jp, Source source) {
        log.info("afterReturning " + jp.toString() + ", return= " + source.toString());
    }

    @Before("callUpdateSource()")
    public void beforeCallAtUpdateSource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callUpdateSource()", returning = "source")
    public void afterReturningCallAtUpdateSource(JoinPoint jp, Source source) {
        log.info("afterReturning " + jp.toString() + ", return= " + source.toString());
    }

    private void printPageSource(JoinPoint jp, Page<Source> pageSource) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                "return=[\n" +
                "   pageCount=" + pageSource.getTotalPages() + ",\n" +
                "   totalElements=" + pageSource.getTotalElements() + ",\n" +
                "   pageNumber=" + pageSource.getPageable().getPageNumber() + ",\n" +
                "   pageSize=" + pageSource.getPageable().getPageSize() + ",\n" +
                "   sort=" + pageSource.getPageable().getSort() + ",\n" +
                "   values=[\n" +
                "       " +
                pageSource.getContent()
                        .stream()
                        .map(Source::toString)
                        .collect(Collectors.joining("\n       ")) +
                "\n" +
                "       ]\n" +
                "]");
    }

}
