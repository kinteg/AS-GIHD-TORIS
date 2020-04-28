package ru.iac.ASGIHDTORIS.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.iac.ASGIHDTORIS.common.model.data.DataModel;
import ru.iac.ASGIHDTORIS.common.model.fulltable.FullTableModel;

import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class FileLoaderControllerAspect {

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.uploadFile(..)) && args(multipartFile,limit,patternId)", argNames = "multipartFile,limit,patternId")
    public void callUploadFile(MultipartFile multipartFile, Long limit, Long patternId) {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.uploadFile(..))")
    public void callUploadFileReturn() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.uploadFirstFile(..)) && args(multipartFile,limit)", argNames = "multipartFile,limit")
    public void callUploadFirstFile(MultipartFile multipartFile, Long limit) {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.uploadFirstFile(..))")
    public void callUploadFirstFileReturn() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.sendData(..)) && args(multipartFile,id)", argNames = "multipartFile,id")
    public void callSendData(MultipartFile multipartFile, Long id) {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.sendData(..))")
    public void callSendDataReturn() {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.sendDates(..)) && args(multipartFile,id)", argNames = "multipartFile,id")
    public void callSendDates(MultipartFile multipartFile, Long id) {
    }

    @Pointcut(value = "execution(public * ru.iac.ASGIHDTORIS.spring.controller.FileLoaderController.sendDates(..))")
    public void callSendDatesReturn() {
    }


    @Before(value = "callUploadFile(multipartFile, limit, patternId)", argNames = "jp,multipartFile,limit,patternId")
    public void beforeCallAtUploadFile(JoinPoint jp, MultipartFile multipartFile, Long limit, Long patternId) {
        log.info("before " + jp.toString() + ",\n   args=[\n" +
                "       fileName=" + multipartFile.getOriginalFilename() + "\n" +
                "       size=" + multipartFile.getSize() + "\n" +
                "       limit=" + limit + "\n" +
                "       patternId=" + patternId + "\n" +
                "   ]");
    }

    @AfterReturning(value = "callUploadFileReturn()", returning = "fullTableModel", argNames = "jp,fullTableModel")
    public void afterReturningCallAtUploadFile(JoinPoint jp, FullTableModel fullTableModel) {
        log.info("afterReturning " + jp.toString() + ", return= " + getStringFullTableModel(jp, fullTableModel));
    }

    @Before(value = "callUploadFirstFile(multipartFile, limit)", argNames = "jp,multipartFile,limit")
    public void beforeCallAtFirstFile(JoinPoint jp, MultipartFile multipartFile, Long limit) {
        log.info("before " + jp.toString() + ",\n   args=[\n" +
                "       fileName=" + multipartFile.getOriginalFilename() + "\n" +
                "       size=" + multipartFile.getSize() + "\n" +
                "       limit=" + limit + "\n" +
                "   ]");
    }

    @AfterReturning(value = "callUploadFirstFileReturn()", returning = "fullTableModel", argNames = "jp,fullTableModel")
    public void afterReturningCallAtFirstFile(JoinPoint jp, FullTableModel fullTableModel) {
        log.info("afterReturning " + jp.toString() + ", return= " + getStringFullTableModel(jp, fullTableModel));
    }

    @Before(value = "callSendData(multipartFile, id)", argNames = "jp,multipartFile,id")
    public void beforeCallAtSendData(JoinPoint jp, MultipartFile multipartFile, Long id) {
        log.info("before " + jp.toString() + ",\n   args=[\n" +
                "       fileName=" + multipartFile.getOriginalFilename() + "\n" +
                "       size=" + multipartFile.getSize() + "\n" +
                "       id=" + id + "\n" +
                "   ]");
    }

    @AfterReturning(value = "callSendDataReturn()", returning = "result")
    public void afterReturningCallAtSendData(JoinPoint jp, boolean result) {
        log.info("before " + jp.toString() + ", result=" + result);
    }

    @Before(value = "callSendDates(multipartFile, id)", argNames = "jp,multipartFile,id")
    public void beforeCallAtSendDates(JoinPoint jp, MultipartFile multipartFile, Long id) {
        log.info("before " + jp.toString() + ",\n   args=[\n" +
                "       fileName=" + multipartFile.getOriginalFilename() + "\n" +
                "       size=" + multipartFile.getSize() + "\n" +
                "       id=" + id + "\n" +
                "\n   ]");
    }

    @AfterReturning(value = "callSendDatesReturn()", returning = "result")
    public void afterReturningCallAtSendDates(JoinPoint jp, boolean result) {
        log.info("before " + jp.toString() + ", result=" + result);
    }

    private String getStringFullTableModel(JoinPoint jp, FullTableModel fullTableModel) {
        return
                "afterReturning " + jp.toString() + ",\n    return=[\n" +
                        "       filename=" + fullTableModel.getTableModel().getFilename() + ",\n" +
                        "       tableName=" + fullTableModel.getTableModel().getTableName() + ",\n" +
                        "       models=[\n" + fullTableModel
                        .getTableModel()
                        .getModels()
                        .stream()
                        .map(DataModel::toString).collect(Collectors.joining("\n       ")) +
                        "\n   ]" + ",\n" +
                        "       values=[\n" +
                        "       " +
                        fullTableModel.getValues()
                                .stream()
                                .map(Map::entrySet)
                                .map(entries -> entries.stream()
                                        .map(v -> v.getKey() + "=" + v.getValue())
                                        .collect(Collectors.joining("\n             "))
                                )
                                .collect(Collectors.joining("\n         ")) +
                        "\n" +
                        "           ]\n" +
                        "\n   ]";
    }

}
