//package net.reduck.jpa.test.report.service;
//
//import net.reduck.jpa.test.report.entity.User;
//import net.reduck.jpa.test.report.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class UserService {
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public User login(String username, String password) {
//        User user = userRepository.findByUsername(username);
//        if (user != null && user.getPassword().equals(password)) {
//            return user;
//        }
//        return null;
//    }
//
//    public User register(String username, String password) {
//        if (userRepository.findByUsername(username) != null) {
//            throw new RuntimeException("用户名已存在");
//        }
//
//        User user = new User();
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setCreateTime(LocalDateTime.now());
//
//        return userRepository.save(user);
//    }
//}
//
