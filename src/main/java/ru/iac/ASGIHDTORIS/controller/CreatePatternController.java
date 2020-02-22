package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/pattern/")
@Slf4j
public class CreatePatternController {

    @PostMapping("/create")
    @ResponseBody
    public String createPattern(
            @RequestParam(value = "file") MultipartFile multipartFile,
            @RequestParam(value = "json") String json,
            @RequestParam(value = "description") String description,
            @RequestParam(value = "direction") String direction,
            @RequestParam(value = "management") String management,
            @RequestParam(value = "source") String source
            ){
        return "ok";
    }
}
