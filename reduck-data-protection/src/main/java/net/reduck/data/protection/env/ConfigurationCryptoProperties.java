package net.reduck.data.protection.env;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Gin
 * @since 2023/5/23 18:55
 */
@Configuration
@ConfigurationProperties(prefix = "configuration.crypto")
@Getter
@Setter
class ConfigurationCryptoProperties {

    public String secretKey;

}
