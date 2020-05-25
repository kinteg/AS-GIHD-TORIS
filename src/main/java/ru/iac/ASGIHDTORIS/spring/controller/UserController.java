package ru.iac.ASGIHDTORIS.spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/source/")
@RestController
public class UserController {

    @GetMapping("/acceptToken/{token}")
    public boolean checkName(@PathVariable String token) {
        return true;
    }

}
