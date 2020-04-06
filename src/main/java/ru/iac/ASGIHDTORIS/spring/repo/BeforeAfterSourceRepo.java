package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.iac.ASGIHDTORIS.spring.domain.BeforeAfterSource;

public interface BeforeAfterSourceRepo extends JpaRepository<BeforeAfterSource, Long> {

    BeforeAfterSource findById(long id);

    Page<BeforeAfterSource> findAll(Pageable pageable);
    Page<BeforeAfterSource> findAllBySourceLoggerId(Pageable pageable, long sourceLoggerId);

}
