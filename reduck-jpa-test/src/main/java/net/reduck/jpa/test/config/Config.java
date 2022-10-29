package net.reduck.jpa.test.config;

import net.reduck.data.protection.DataProtectionFactory;
import net.reduck.data.protection.core.DataProtectionModule;
import net.reduck.data.protection.core.ProtectionPassword;
import net.reduck.data.protection.core.ProtectionPhoneNo;
import net.reduck.data.protection.core.protection.PasswordProtector;
import net.reduck.data.protection.core.protection.PhoneNoProtector;
import net.reduck.jpa.specification.NativeQueryExecutor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.Ordered;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Reduck
 * @since 2022/8/26 12:00
 */
@Configuration
public class Config {

    @Bean
    @Scope("prototype")
    Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder(ApplicationContext applicationContext
            , List<Jackson2ObjectMapperBuilderCustomizer> customizers
            , DataProtectionFactory dataProtectionFactory) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.applicationContext(applicationContext);
        customize(builder, customizers);
        builder.modules(new DataProtectionModule(dataProtectionFactory));
        return builder;
    }

    @Bean
    @ConditionalOnMissingBean
    DataProtectionFactory dataProtectionFactory() {
        DataProtectionFactory dataProtectionFactory = new DataProtectionFactory();
        dataProtectionFactory.register(ProtectionPassword.class, new PasswordProtector());
        dataProtectionFactory.register(ProtectionPhoneNo.class, new PhoneNoProtector());
        return dataProtectionFactory;
    }

    private void customize(Jackson2ObjectMapperBuilder builder,
                           List<Jackson2ObjectMapperBuilderCustomizer> customizers) {
        for (Jackson2ObjectMapperBuilderCustomizer customizer : customizers) {
            customizer.customize(builder);
        }
    }

    @Bean
    public NativeQueryExecutor nativeQueryExecutor(JdbcTemplate jdbcTemplate) {
        return new NativeQueryExecutor(jdbcTemplate);
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
                registry.addResourceHandler("/images/**").addResourceLocations("classpath:/images2/");
            }
        };
    }
}
