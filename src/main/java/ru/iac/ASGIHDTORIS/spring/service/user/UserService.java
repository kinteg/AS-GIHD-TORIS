package ru.iac.ASGIHDTORIS.spring.service.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.iac.ASGIHDTORIS.spring.domain.User;

@Service
public interface UserService extends UserDetailsService {

    User loginUser(String token);

}
