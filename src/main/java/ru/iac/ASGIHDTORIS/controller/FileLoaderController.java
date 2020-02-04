package ru.iac.ASGIHDTORIS.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/")
public class FileLoaderController {

    @PostMapping("single-file")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {

        if (multipartFile == null)
            return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND).toString();
        else {
            System.out.println(multipartFile.getContentType());
            return "хуй";
        }
    }
}
