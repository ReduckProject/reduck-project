//package net.reduck.jpa.test.report.entity;
//
//import lombok.Data;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Entity
//@Table(name = "comments")
//@Data
//public class Comment implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String content;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "report_id")
//    private Report report;
//
//    // constructors, getters and setters, equals and hashCode methods
//}
