package ru.iac.ASGIHDTORIS.spring.controller.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.iac.ASGIHDTORIS.spring.domain.Pattern;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class PatternControllerAspect {

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.createPattern(..))")
    public void callCreatePattern() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getPatternById(..))")
    public void callGetById() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPattern(..))")
    public void callGetAll() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternSort(..))")
    public void callGetAllSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternBySourceId(..))")
    public void callGetAllBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternBySourceIdSort(..))")
    public void callGetAllBySourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternArchive(..))")
    public void callGetAllArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternArchiveSort(..))")
    public void callGetAllArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternArchiveBySourceId(..))")
    public void callGetAllArchiveBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternArchiveBySourceIdSort(..))")
    public void callGetAllArchiveBySourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternNotArchive(..))")
    public void callGetAllNotArchive() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternNotArchiveSort(..))")
    public void callGetAllNotArchiveSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternNotArchiveBySourceId(..))")
    public void callGetAllNotArchiveBySourceId() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.getAllPatternNotArchiveBuSourceIdSort(..))")
    public void callGetAllNotArchiveBuSourceIdSort() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.archivePattern(..))")
    public void callArchivePattern() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.deArchivePattern(..))")
    public void callDeArchivePattern() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.archivePatterns(..))")
    public void callArchivePatterns() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.deArchivePatterns(..))")
    public void callDeArchivePatterns() {
    }

    @Pointcut("execution(public * ru.iac.ASGIHDTORIS.spring.controller.PatternController.update(..))")
    public void callUpdate() {
    }

    @Before("callCreatePattern()")
    public void beforeCallAtCreateSource(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callCreatePattern()", returning = "pattern")
    public void afterReturningCallAtCreateSource(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callGetById()")
    public void beforeCallAtGetById(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetById()", returning = "pattern")
    public void afterReturningCallAtGetById(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callGetAll()")
    public void beforeCallAtGetAll(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAll()", returning = "pagePattern")
    public void afterReturningCallAtGetAll(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllSort()")
    public void beforeCallAtGetAllSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllBySourceId()")
    public void beforeCallAtGetAllBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllBySourceId()", returning = "pagePattern")
    public void afterReturningCallAtGetAllBySourceId(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllBySourceIdSort()")
    public void beforeCallAtGetAllBySourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllBySourceIdSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllBySourceIdSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllArchive()")
    public void beforeCallAtGetAllArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchive()", returning = "pagePattern")
    public void afterReturningCallAtGetAllArchive(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllArchiveSort()")
    public void beforeCallAtGetAllArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllArchiveSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllArchiveBySourceId()")
    public void beforeCallAtGetAllArchiveBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveBySourceId()", returning = "pagePattern")
    public void afterReturningCallAtGetAllArchiveBySourceId(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllArchiveBySourceIdSort()")
    public void beforeCallAtGetAllArchiveBySourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllArchiveBySourceIdSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllArchiveBySourceIdSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllNotArchive()")
    public void beforeCallAtGetAllNotArchive(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchive()", returning = "pagePattern")
    public void afterReturningCallAtGetAllNotArchive(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllNotArchiveSort()")
    public void beforeCallAtGetAllNotArchiveSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllNotArchiveSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllNotArchiveBySourceId()")
    public void beforeCallAtGetAllNotArchiveBySourceId(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveBySourceId()", returning = "pagePattern")
    public void afterReturningCallAtGetAllNotArchiveBySourceId(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callGetAllNotArchiveBuSourceIdSort()")
    public void beforeCallAtGetAllNotArchiveBuSourceIdSort(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callGetAllNotArchiveBuSourceIdSort()", returning = "pagePattern")
    public void afterReturningCallAtGetAllNotArchiveBuSourceIdSort(JoinPoint jp, Page<Pattern> pagePattern) {
        printPageSource(jp, pagePattern);
    }

    @Before("callArchivePattern()")
    public void beforeCallAtArchivePattern(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchivePattern()", returning = "pattern")
    public void afterReturningCallAtArchivePattern(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callDeArchivePattern()")
    public void beforeCallAtDeArchivePattern(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchivePattern()", returning = "pattern")
    public void afterReturningCallAtDeArchivePattern(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callArchivePatterns()")
    public void beforeCallAtArchivePatterns(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callArchivePatterns()", returning = "pattern")
    public void afterReturningCallAtArchivePatterns(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callDeArchivePatterns()")
    public void beforeCallAtDeArchivePatterns(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callDeArchivePatterns()", returning = "pattern")
    public void afterReturningCallAtDeArchivePatterns(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    @Before("callUpdate()")
    public void beforeCallAtUpdate(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(",\n"));
        log.info("before " + jp.toString() + ", args=[" + args + "]");
    }

    @AfterReturning(value = "callUpdate()", returning = "pattern")
    public void afterReturningCallAtUpdate(JoinPoint jp, Pattern pattern) {
        log.info("afterReturning " + jp.toString() + ", return= " + pattern.toString());
    }

    private void printPageSource(JoinPoint jp, Page<Pattern> pagePattern) {
        log.info("afterReturning " + jp.toString() + ",\n" +
                "return=[\n" +
                "   pageCount=" + pagePattern.getTotalPages() + ",\n" +
                "   totalElements=" + pagePattern.getTotalElements() + ",\n" +
                "   pageNumber=" + pagePattern.getPageable().getPageNumber() + ",\n" +
                "   pageSize=" + pagePattern.getPageable().getPageSize() + ",\n" +
                "   sort=" + pagePattern.getPageable().getSort() + ",\n" +
                "   values=[\n" +
                "       " +
                pagePattern.getContent()
                        .stream()
                        .map(Pattern::toString)
                        .collect(Collectors.joining("\n       ")) +
                "\n" +
                "       ]\n" +
                "]");
    }

}
