package ru.iac.ASGIHDTORIS.spring.service.user;

import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Service
public interface UserService {

    User loginUser(String token);

}
