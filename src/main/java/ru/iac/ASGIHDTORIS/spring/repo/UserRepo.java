package ru.iac.ASGIHDTORIS.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    boolean existsBySecretKey(String uid);

    User findBySecretKey(String secret);

}
