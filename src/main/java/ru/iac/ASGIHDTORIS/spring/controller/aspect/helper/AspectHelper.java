package ru.iac.ASGIHDTORIS.spring.controller.aspect.helper;

import org.aspectj.lang.JoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public interface AspectHelper<T> {

    String getPagePattern(JoinPoint jp, Page<T> pagePattern);

}
