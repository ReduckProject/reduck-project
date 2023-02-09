package net.reduck.jpa.test.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Reduck
 * @since 2022/12/1 11:32
 */
@RestController
@RequestMapping(value = "/upload")
public class UploadController {

    @PostMapping(value = "/upload", name = "上传")
    @ApiOperation(value = "上传")
    public String upload(@RequestParam MultipartFile file) {
        return "success";
    }
}
