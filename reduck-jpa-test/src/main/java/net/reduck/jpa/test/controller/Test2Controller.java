package net.reduck.jpa.test.controller;

import net.reduck.jpa.processor.Hello;
import net.reduck.jpa.test.vo.UserListTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reduck
 * @since 2022/8/29 15:03
 */
@RestController
@RequestMapping(value = "/test2")
public class Test2Controller {

    @GetMapping(value = "/notNull")
    @Hello
    public String notNull(@Validated(value = Test2Controller.class) UserListTO to) {
        return ":" + to.getUsername();
    }

    @RequestMapping(value = "/check")
    public String check(){
        return "{\"status\" : 0}";
    }
}
