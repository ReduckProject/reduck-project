package net.reduck.data.protection.env;

import net.reduck.data.protection.env.encryptor.SimplePBEByteEncryptor;
import org.jasypt.salt.RandomSaltGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/**
 * @author Reduck
 * @since 2022/9/26 16:46
 */
@Order(value = Ordered.LOWEST_PRECEDENCE)
public class EncryptionEnvironmentPostProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        MutablePropertySources mutablePropertySources = environment.getPropertySources();

        for (PropertySource<?> propertySource : mutablePropertySources) {
            if (propertySource instanceof OriginTrackedMapPropertySource) {
                mutablePropertySources.replace(propertySource.getName(),
                        new PropertySourceWrapper(propertySource, initSimpleEncryptor("reduck-project")
                                , new EncryptionWrapperDetector("$ENC{", "}"))
                );
            }
        }
    }

    private SimpleEncryptor initSimpleEncryptor(String password) {
        SimplePBEByteEncryptor encryptor = new SimplePBEByteEncryptor();
        encryptor.setPassword(password);
        encryptor.setSaltGenerator(new RandomSaltGenerator());
        encryptor.setIterations(64);
        encryptor.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");

        return new SimpleEncryptor(encryptor);
    }
}
