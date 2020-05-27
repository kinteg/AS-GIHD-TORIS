package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.repo.UserRepo;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;

@RequestMapping("api/user/")
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;

    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

    @SneakyThrows
    @GetMapping("/acceptToken/{token}")
    public User login(@PathVariable String token) {
        return userService.loginUser(token);
    }

    @GetMapping("isAdmin/{token}")
    public boolean isAdmin(@PathVariable String token) {
        return userRepo.existsBySecretKey(userService.loginUser(token).getSecretKey());
    }

}
