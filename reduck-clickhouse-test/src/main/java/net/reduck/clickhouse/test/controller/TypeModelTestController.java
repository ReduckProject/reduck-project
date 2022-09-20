package net.reduck.clickhouse.test.controller;

import net.reduck.clickhouse.test.entity.TypeModel;
import net.reduck.clickhouse.test.repository.TypeModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Reduck
 * @since 2022/9/17 15:47
 */
@RestController
@RequestMapping(value = "/typeModelTest")
//@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TypeModelTestController {
    @Autowired
    private TypeModelRepository repository;

    @RequestMapping(value = "/test")
    public Object test(){
        return repository.findAll();
    }

    @RequestMapping(value = "/persist")
    public String persist(@RequestParam(required = false) int count){
        if(count == 0){
            count = 100;
        }
        for(int i = 0; i < count; i++){
            TypeModel model = new TypeModel();
            model.setId((long)i);
//            model.setPhoneNo("['18655445270', 'NULL']");
            model.setVersion(0);
            model.setName("test" + "-" + i);
//            model.setUpdateTime(System.currentTimeMillis() / 1000);
            repository.save(model);
        }

        return "success" + " " + count;
    }
}
