//package net.reduck.jpa.test.report.repository;
//
//import net.reduck.jpa.test.report.entity.Report;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Repository
//public interface ReportRepository extends JpaRepository<Report, Long> {
//
//    List<Report> findByUserIdOrderByCreateTimeDesc(Long userId);
//
//    List<Report> findByContentContainingOrderByCreateTimeDesc(String keywords);
//
//    List<Report> findByCreateTimeBetweenOrderByCreateTimeDesc(LocalDateTime start, LocalDateTime end);
//
//}
