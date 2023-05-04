package net.reduck.jpa.test.report;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Gin
 * @since 2023/4/24 09:58
 */
@RestController
@RequestMapping(value = "/report")
public class ReportController {

    @GetMapping(value = "/list")
    @CrossOrigin(value = "*")
    public List<Object> list() {
        ReportListVO vo = new ReportListVO();
        vo.tag = "tag";
        vo.content = "测试数据";
        vo.createTime = "2023-03-23";
        vo.updateTime = "2023-03-23";

        return Arrays.asList(vo, vo, vo);
    }

    @Data
    public static class ReportListVO {
        private String tag;

        private String content;

        private String createTime;

        private String updateTime;
    }
}
