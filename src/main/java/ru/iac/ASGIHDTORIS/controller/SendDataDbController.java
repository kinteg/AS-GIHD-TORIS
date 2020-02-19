package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@Slf4j
public class SendDataDbController {

    @PostMapping("/sendData")
    @ResponseBody
    public String sendData(
            @RequestParam(value = "file", required = false)
                    MultipartFile multipartFile,
            @RequestParam(value = "json", required = false)
                    String json){
        log.info(json +"");

        return "Ok";
    }
}