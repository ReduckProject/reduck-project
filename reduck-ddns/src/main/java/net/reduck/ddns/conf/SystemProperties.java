package net.reduck.ddns.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/15 11:03
 */
@ConfigurationProperties(prefix = "reduck.ddns")
@Configuration
@Data
public class SystemProperties {

    private String ipServiceUrl;

    private Domain domain;

    private Access access;

    @Data
    public static class Domain {
        private String domainName;

        private List<String> subDomains;

        private String ip;

        private String regionId;

        private String type;
    }

    @Data
    public static class Access {
        private String accessKey;

        private String secretKey;
    }
}
