package net.reduck.jpa.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.reduck.jpa.specification.NativeQueryExecutor;
import net.reduck.jpa.test.entity.PersonalInfo;
import net.reduck.jpa.test.entity.User;
import net.reduck.jpa.test.repository.UserRepository;
import net.reduck.jpa.test.service.TestService;
import net.reduck.jpa.test.service.TransactionalService;
import net.reduck.jpa.test.vo.UserListTO;
import net.reduck.jpa.test.vo.UserListVO;
import net.reduck.jpa.test.vo.UserVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/6 23:07
 */
@RestController
@RequestMapping(value = "/test")
@RequiredArgsConstructor
@Log4j2
public class TestController {
    private final UserRepository userRepository;
    private final EntityManager entityManager;
    private final TransactionalService transactionalService;
    private final NativeQueryExecutor executor;

    private final TestService testService;
    @Value("${enc.test}")
    private String value;

    @Value("${oem.test}")
    private String test;

    /**
     * 列表
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list")
    public Object list(HttpServletRequest servletRequest, @Validated UserListTO request) {
        log.info("INFO____");
        log.warn("WARN___");
        log.error("ERROR___d");
//        throw new RuntimeException("");
        userRepository.executeNativeSql("select * from user", UserVO.class);
        return userRepository.findAll();
    }

    @GetMapping(value = "/all")
    public Object load(HttpServletRequest servletRequest, @Validated UserListTO request) {
        return userRepository.findAll();
    }

    @GetMapping(value = "/jpa")
    public Object jpaTest() {
        return testService.test();
    }

    /**
     * 列表2
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/list2")
    public List<?> list2(UserListTO request) {
//        userRepository.findAllByBuilder(SpecificationQueryBuilder.newInstance()
//                .and("id").operate(OperatorType.IN).value(Arrays.asList(10,11)).match()
//                .or("username").operate(OperatorType.CONTAIN).value("3").match());
        return userRepository.findAllWith(request, UserVO.class);
    }

    /**
     * 跳转
     *
     * @param response
     * @throws IOException
     */
    @GetMapping(value = "/jump")
    public void list2(HttpServletResponse response) throws IOException {
        response.sendRedirect("/test/list");
    }

    @GetMapping(value = "/tx")
    public void tx(){
        transactionalService.test();
    }

    @GetMapping(value = "/enc")
    public String enc(){
        return value;
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

    @RequestMapping(value = "/transformer")
    public Object transformer(UserListTO to){
        return userRepository.findAllWith(to, UserListVO.class);
    }

    @GetMapping(value = "/nativeQuery")
    public List<UserVO> nativeQuery(){
        return executor.query("select * from user where id > ?1", UserVO.class, 1);
    }

    public static void main(String[] args) {
        new RestTemplate().getForEntity("https://www.baidu.com", String.class);
    }
}
