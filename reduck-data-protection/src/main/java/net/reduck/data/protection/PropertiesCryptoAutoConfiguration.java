package net.reduck.data.protection;

import net.reduck.data.protection.env.BeanFinder;
import net.reduck.data.protection.env.EncryptionBeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author Gin
 * @since 2023/5/22 11:24
 */
@Configuration
@ConditionalOnProperty(prefix = "configuration.crypto", name = "enabled", matchIfMissing = true)
public class PropertiesCryptoAutoConfiguration {

    @Bean
    public static EncryptionBeanPostProcessor encryptionBeanPostProcessor(final ConfigurableEnvironment environment) {
        return new EncryptionBeanPostProcessor(environment);
    }

    @Bean
    public static BeanFinder beanFinder(ApplicationContext context) {
        return new BeanFinder(context);
    }
}
