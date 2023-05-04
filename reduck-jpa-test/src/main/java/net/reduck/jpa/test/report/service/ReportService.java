//package net.reduck.jpa.test.report.service;
//
//import net.reduck.jpa.test.report.entity.Comment;
//import net.reduck.jpa.test.report.entity.Report;
//import net.reduck.jpa.test.report.repository.ReportRepository;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class ReportService {
//    private final ReportRepository reportRepository;
//
//    public ReportService(ReportRepository reportRepository) {
//        this.reportRepository = reportRepository;
//    }
//
//    public List<Report> getReportsByUserId(Long userId) {
//        return reportRepository.findByUserId(userId);
//    }
//
//    public Report createReport(Report report) {
//        report.setId(null);
//        report.setCreateTime(LocalDateTime.now());
//
//        return reportRepository.save(report);
//    }
//
//    public Report getReportById(Long id) {
//        return reportRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("未找到指定的日报记录"));
//    }
//
//    public Report updateReport(Report report) {
//        if (reportRepository.existsById(report.getId())) {
//            report.setUpdateTime(LocalDateTime.now());
//            return reportRepository.save(report);
//        } else {
//            throw new RuntimeException("未找到指定的日报记录");
//        }
//    }
//
//    public void deleteReport(Long id) {
//        if (reportRepository.existsById(id)) {
//            reportRepository.deleteById(id);
//        } else {
//            throw new RuntimeException("未找到指定的日报记录");
//        }
//    }
//
//    public void addComment(Long reportId, String comment) {
//        Report report = reportRepository.findById(reportId)
//                .orElseThrow(() -> new RuntimeException("未找到指定的日报记录"));
//
//        List<Comment> comments = report.getComments();
//        if (comments == null) {
//            comments = new ArrayList<>();
//        }
//        comments.add(comment);
//
//        report.setComments(comments);
//        reportRepository.save(report);
//    }
//
//    public List<Report> searchReportsByKeywords(String keywords) {
//        return reportRepository.findByContentContaining(keywords);
//    }
//
//    public List<Report> searchReportsByDate(LocalDate startDate, LocalDate endDate) {
//        return reportRepository.findByCreateTimeBetween(startDate.atStartOfDay(), endDate.atTime(LocalTime.MAX));
//    }
//}
