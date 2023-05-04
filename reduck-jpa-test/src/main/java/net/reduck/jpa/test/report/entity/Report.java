//package net.reduck.jpa.test.report.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Table(name = "reports")
//@Data
//public class Report implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotNull
//    @Size(max = 100)
//    private String title;
//
//    @NotNull
//    @Size(max = 5000)
//    private String content;
//
//    @NotNull
//    private LocalDate reportDate;
//
//    @NotNull
//    private LocalDateTime createTime;
//
//    @NotNull
//    private LocalDateTime updateTime;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @OneToMany(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
//    private List<Comment> comments;
//    // constructors, getters and setters, equals and hashCode methods
//}
//
