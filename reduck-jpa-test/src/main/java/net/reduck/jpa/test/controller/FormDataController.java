package net.reduck.jpa.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gin
 * @since 2023/7/11 16:05
 */

@RestController
@RequestMapping(value = "/formData")
public class FormDataController {

    @RequestMapping(value = "/test")
    public String test(String type,  String data) {
        return type + "\n" + data;
    }
}
