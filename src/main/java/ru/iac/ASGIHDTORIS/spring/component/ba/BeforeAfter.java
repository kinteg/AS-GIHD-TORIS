package ru.iac.ASGIHDTORIS.spring.component.ba;

import org.springframework.stereotype.Component;

@Component
public interface BeforeAfter<T> {

    void afterCreate(T after, Long loggerId);

    void afterArchive(T before, T after, Long loggerId);

    void afterDeArchive(T before, T after, Long loggerId);

    void afterUpdate(T before, T after, Long loggerId);

}
