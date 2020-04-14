package ru.iac.ASGIHDTORIS.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.PatternFile;
import ru.iac.ASGIHDTORIS.spring.domain.PatternTableFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class FileUnLoaderControllerAspect {

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.findPatternFileById(..))")
    public void callFindPatternFileById() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getAllPatternFile(..))")
    public void callGetAllPatternFile() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getAllPatternFileByPatternId(..))")
    public void callGetAllPatternFileByPatternId() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.findPatternTableFileById(..))")
    public void callFindPatternTableFileById() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getAllPatternTableFile(..))")
    public void callGetAllPatternTableFile() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getAllPatternTableFileByPatternId(..))")
    public void callGetAllPatternTableFileByPatternId() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getPatternFile(..))")
    public void callGetPatternFile() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileUnLoaderController.getPatternTableFile(..))")
    public void callGetPatternTableFile() {
    }


    @Before(value = "callFindPatternFileById()", argNames = "jp")
    public void beforeCallAtFindPatternFileById(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }


    @AfterReturning(value = "callFindPatternFileById()", returning = "patternFile", argNames = "jp,patternFile")
    public void afterReturningCallAtFindPatternFileById(JoinPoint jp, PatternFile patternFile) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternFile.toString());
    }

    @Before(value = "callGetAllPatternFile()", argNames = "jp")
    public void beforeCallAtGetAllPatternFile(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllPatternFile()", returning = "patternFilePage", argNames = "jp,patternFilePage")
    public void afterReturningCallAtGetAllPatternFile(JoinPoint jp, Page<PatternFile> patternFilePage) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                "return=[\n" +
                "   pageCount=" + patternFilePage.getTotalPages() + ",\n" +
                "   totalElements=" + patternFilePage.getTotalElements() + ",\n" +
                "   pageNumber=" + patternFilePage.getPageable().getPageNumber() + ",\n" +
                "   pageSize=" + patternFilePage.getPageable().getPageSize() + ",\n" +
                "   sort=" + patternFilePage.getPageable().getSort() + ",\n" +
                "   values=[\n" +
                "       " +
                patternFilePage.getContent()
                        .stream()
                        .map(PatternFile::toString)
                        .collect(Collectors.joining("\n       ")) +
                "\n" +
                "       ]\n" +
                "]");
    }

    @Before(value = "callGetAllPatternFileByPatternId()", argNames = "jp")
    public void beforeCallAtGetAllPatternFileByPatternId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllPatternFileByPatternId()", returning = "patternFiles", argNames = "jp,patternFiles")
    public void afterReturningCallAtGetAllPatternFileByPatternId(JoinPoint jp, List<PatternFile> patternFiles) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                patternFiles
                        .stream()
                        .map(PatternFile::toString)
                        .collect(Collectors.joining("\n       "))
        );
    }

    @Before(value = "callFindPatternTableFileById()", argNames = "jp")
    public void beforeCallAtFindPatternTableFileById(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callFindPatternTableFileById()", returning = "patternTableFile", argNames = "jp,patternTableFile")
    public void afterReturningCallAtFindPatternTableFileById(JoinPoint jp, PatternTableFile patternTableFile) {
        log.info("afterReturning " + jp.toString() + ", return= " + patternTableFile.toString());
    }

    @Before(value = "callGetAllPatternTableFile()", argNames = "jp")
    public void beforeCallAtGetAllPatternTableFile(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllPatternTableFile()", returning = "patternTableFilePage", argNames = "jp,patternTableFilePage")
    public void afterReturningCallAtGetAllPatternTableFile(JoinPoint jp, Page<PatternTableFile> patternTableFilePage) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                "return=[\n" +
                "   pageCount=" + patternTableFilePage.getTotalPages() + ",\n" +
                "   totalElements=" + patternTableFilePage.getTotalElements() + ",\n" +
                "   pageNumber=" + patternTableFilePage.getPageable().getPageNumber() + ",\n" +
                "   pageSize=" + patternTableFilePage.getPageable().getPageSize() + ",\n" +
                "   sort=" + patternTableFilePage.getPageable().getSort() + ",\n" +
                "   values=[\n" +
                "       " +
                patternTableFilePage.getContent()
                        .stream()
                        .map(PatternTableFile::toString)
                        .collect(Collectors.joining("\n       ")) +
                "\n" +
                "       ]\n" +
                "]");
    }

    @Before(value = "callGetAllPatternTableFileByPatternId()")
    public void beforeCallAtGetAllPatternTableFileByPatternId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllPatternTableFileByPatternId()", returning = "patternTableFiles")
    public void afterReturningCallAtGetAllPatternTableFileByPatternId(JoinPoint jp, List<PatternTableFile> patternTableFiles) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                patternTableFiles
                        .stream()
                        .map(PatternTableFile::toString)
                        .collect(Collectors.joining("\n       "))
        );
    }

    @Before(value = "callGetPatternFile()")
    public void beforeCallAtGetPatternFile(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @After(value = "callGetPatternFile()")
    public void afterCallAtGetPatternFile(JoinPoint jp) {
        log.info("after " + jp.toString());
    }

    @Before(value = "callGetPatternTableFile()")
    public void beforeCallAtGetPatternTableFile(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @After(value = "callGetPatternTableFile()")
    public void afterCallAtGetPatternTableFile(JoinPoint jp) {
        log.info("after " + jp.toString());
    }

}
