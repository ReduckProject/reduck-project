package net.reduck.jpa.test.controller;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gin
 * @since 2022/11/21 17:05
 */
@Configuration
@ConfigurationProperties(prefix = "txc.cas")
@ConditionalOnProperty(prefix = "txc.cas", value = "enabled", matchIfMissing = false)
@Data
public class CasProperties {

    /**
     * 是否启用
     */
    private boolean enabled = true;

    /**
     * CAS校验URL[第三方登录成功后，回调我方地址]
     */
    private String callbackUrl = "https://gintest.com/cas/callback";

    /**
     * 第三方访问地址
     */
    private String remoteUrl = "https://sjcy-idp.yufuid.com/sso/tn-20b4a4fff53b4574af2c125ab01da6d4/ai-13d6bc4b75154841a5ba3a65fea6b214/oidc";

    /**
     * 客户端ID
     */
    private String clientId = "ai-13d6bc4b75154841a5ba3a65fea6b214";

    /**
     * 客户端凭据
     */
    private String clientSecret = "jQiUFqTXazVTdZsXWiyL9uw1";

    /**
     * 安全管理员Code
     */
    private Long roleId;

}
