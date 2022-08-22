package net.reduck.jpa.test.controller;

import net.reduck.jpa.specification.OperatorType;
import net.reduck.jpa.specification.SpecificationQueryBuilder;
import net.reduck.jpa.test.entity.PersonalInfo;
import net.reduck.jpa.test.entity.User;
import net.reduck.jpa.test.repository.UserRepository;
import net.reduck.jpa.test.vo.UserListTO;
import net.reduck.jpa.test.vo.UserVO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/6 23:07
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    private final UserRepository userRepository;

    public TestController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/list")
    public Object list(UserListTO request) {
        return userRepository.findAll();
    }

    @GetMapping(value = "/list2")
    public List<?> list2(UserListTO request) {
//        userRepository.findAllByBuilder(SpecificationQueryBuilder.newInstance()
//                .and("id").operate(OperatorType.IN).value(Arrays.asList(10,11)).match()
//                .or("username").operate(OperatorType.CONTAIN).value("3").match());
        return userRepository.findAllNoPageWith(request, UserVO.class);
    }

    @Transactional
    @RequestMapping(value = "/add")
    public void add() {
        User user = new User();
        user.setPassword("123456");
        user.setUsername("test");

        PersonalInfo personalInfo = new PersonalInfo();
        personalInfo.setEmail(user.getUsername() + "@gmail.com");
        personalInfo.setPhoneNo("18544332567");

        user.setInfo(personalInfo);
        userRepository.save(user);
    }
}
