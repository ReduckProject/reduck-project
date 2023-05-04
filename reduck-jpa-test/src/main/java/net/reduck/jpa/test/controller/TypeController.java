package net.reduck.jpa.test.controller;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Gin
 * @since 2023/4/20 09:54
 */
@RestController
@RequestMapping(value = "/type")
public class TypeController {

    /**
     * 获取数组
     *
     *
     * @return
     */
    @RequestMapping(value = "/array")
    public Map<String, Object> get(){
        Map<String, Object> map = new HashMap<>();
        List list = new ArrayList();
        list.add("3");
        list.add("5");
        list.add(8);

        map.put("test", list);
        map.put("test2", list.toString());

        return map;
    }


    /**
     * 获取数组2
     *
     * @param test 测试
     * @return
     */
    @RequestMapping(value = "/array2")
    public Object set(@RequestBody Test test){
        System.out.println(test.toString());
        return test;
    }

    @Data
    public static class Test {
        String test;
    }
}
