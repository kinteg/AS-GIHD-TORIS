package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iac.ASGIHDTORIS.spring.domain.Role;
import ru.iac.ASGIHDTORIS.spring.domain.SourceSet;
import ru.iac.ASGIHDTORIS.spring.domain.User;
import ru.iac.ASGIHDTORIS.spring.repo.SourceRepo;
import ru.iac.ASGIHDTORIS.spring.repo.SourceSetRepo;
import ru.iac.ASGIHDTORIS.spring.repo.UserRepo;
import ru.iac.ASGIHDTORIS.spring.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequestMapping("api/user/")
@RestController
public class UserController {

    private final UserService userService;
    private final UserRepo userRepo;
    private final SourceSetRepo sourceSetRepo;
    private final SourceRepo sourceRepo;

    public UserController(UserService userService, UserRepo userRepo, SourceSetRepo sourceSetRepo, SourceRepo sourceRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
        this.sourceSetRepo = sourceSetRepo;
        this.sourceRepo = sourceRepo;
    }

    @SneakyThrows
    @GetMapping("/acceptToken/{token}")
    public User login(@PathVariable String token) {
        return userService.loginUser(token);
    }

    @GetMapping("isAdmin/{token}")
    public boolean isAdmin(@PathVariable String token) {
        return userRepo.findBySecretKey(userService.loginUser(token).getSecretKey()).getRoles().contains(Role.ADMIN);
    }

    @GetMapping("isUser/{token}")
    public boolean isUser(@PathVariable String token) {
        return userRepo.existsBySecretKey(userService.loginUser(token).getSecretKey());
    }
//может ли юзер менять сурс
    @GetMapping("isChangeSource/{token}/{sourceId}")
    public boolean isChangeSource(@PathVariable String token, @PathVariable Long sourceId) {
        User user = userService.loginUser(token);

        return user.getRoles().contains(Role.ADMIN) ||
                sourceSetRepo.existsBySourceIdAndUserId(sourceId, user.getId());
    }

    @GetMapping("setUserInSource/{token}/{sourceId}/{secret}")
    public boolean setUserInSource(
            @PathVariable String token, @PathVariable Long sourceId, @PathVariable String secret) {

        if (isAdmin(token) && sourceRepo.existsById(sourceId) && userRepo.existsBySecretKey(secret)) {
            long id = userRepo.findBySecretKey(secret).getId();
            if(sourceSetRepo.existsBySourceIdAndUserId(sourceId, id)) {
                return false;
            }

            sourceSetRepo.save(
                    SourceSet
                            .builder()
                            .userId(id)
                            .sourceId(sourceId)
                            .build());
            return true;
        }

        return false;
    }

    @GetMapping("deleteUserInSource/{token}/{sourceId}/{secret}")
    public boolean deleteUserInSource(
            @PathVariable String token, @PathVariable Long sourceId, @PathVariable String secret) {

        if (isAdmin(token) && sourceRepo.existsById(sourceId) && userRepo.existsBySecretKey(secret)) {
            SourceSet sourceSet = sourceSetRepo.findBySourceIdAndUserId(sourceId, userRepo.findBySecretKey(secret).getId());
            sourceSetRepo.delete(sourceSet);
            return true;
        }

        return false;
    }

    @GetMapping("getAllUser/")
    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    @GetMapping("getAllUserWithSource/{sourceId}")
    public List<User> getAllUserWithSource(@PathVariable long sourceId) {
        return sourceSetRepo.findAllBySourceId(sourceId)
                .stream().map(v -> userRepo.findById((long)v.getUserId())).collect(Collectors.toList());
    }

    @GetMapping("getAllUserWithoutSource/{sourceId}")
    public List<User> getAllUserWithoutSource(@PathVariable long sourceId) {
        List<User> list = userRepo.findAll();
        list.removeAll(sourceSetRepo.findAllBySourceId(sourceId)
                .stream().map(v -> userRepo.findById((long)v.getUserId())).collect(Collectors.toList()));

        return list;
    }

}
