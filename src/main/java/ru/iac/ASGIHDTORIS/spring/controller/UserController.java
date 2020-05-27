package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;

@RequestMapping("api/user/")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @GetMapping("/acceptToken/{token}")
    public User login(@PathVariable String token) {

        userService.loginUser(token);
        return userService.loginUser(token);
    }

}
