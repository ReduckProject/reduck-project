package net.reduck.jpa.test.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reduck
 * @since 2022/9/29 09:17
 */
@RestController
public class OrderController {

    @GetMapping(value = "/nice.html")
    public String nice(){
        return "nice";
    }
}
