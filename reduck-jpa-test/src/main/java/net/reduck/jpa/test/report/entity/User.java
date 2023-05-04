//package net.reduck.jpa.test.report.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "users")
//@Data
//public class User implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true)
//    private String username;
//
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    private LocalDateTime createTime;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Report> reports = new ArrayList<>();
//
//    // constructors, getters and setters, equals and hashCode methods
//}
