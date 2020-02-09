package ru.iac.ASGIHDTORIS.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("api/")
public class FileLoaderController {

    @PostMapping("select-files")
    @ResponseBody
    public String upload(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {

        log.info(files.length+"");
//        for(MultipartFile file : files) {
//            log.info(file.getContentType() + "ъуъ");
//        }
        return "ok";
    }

//    public String uploadFile(@RequestParam("file") MultipartFile multipartFile) {
//
//        if (multipartFile == null)
//            return new ResponseEntity<String>("Error", HttpStatus.NOT_FOUND).toString();
//        else {
//            System.out.println(multipartFile.getContentType());
//            return "";
//        }
//    }
}
