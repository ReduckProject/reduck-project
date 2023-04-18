package net.reduck;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;

/**
 * @author Gin
 * @since 2023/2/15 14:55
 */
@RestController
public class WebServiceController {


    @RequestMapping(value = "/WSSmCommLower/WSSmCommLower")
    public String process(@RequestBody String data, HttpServlet servlet) {
        System.out.println(data);
        return "1";
    }
}
