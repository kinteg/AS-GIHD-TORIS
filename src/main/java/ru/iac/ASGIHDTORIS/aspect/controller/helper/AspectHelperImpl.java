package ru.iac.ASGIHDTORIS.aspect.controller.helper;

import org.aspectj.lang.JoinPoint;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AspectHelperImpl<T> {

    public String getPagePattern(JoinPoint jp, Page<T> pagePattern) {
        return
                "afterReturning " + jp.toString() + ",\n" +
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
                                .map(T::toString)
                                .collect(Collectors.joining("\n       ")) +
                        "\n" +
                        "       ]\n" +
                        "]";
    }

}
