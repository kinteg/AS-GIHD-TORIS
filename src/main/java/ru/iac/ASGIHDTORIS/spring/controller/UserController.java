package ru.iac.ASGIHDTORIS.spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RequestMapping("api/user/")
@RestController
public class UserController {

    @GetMapping("/acceptToken/{token}")
    public boolean checkName(@PathVariable String token) {
        log.info(token);
        return true;
    }

}
