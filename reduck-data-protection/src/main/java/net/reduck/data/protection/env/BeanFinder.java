package net.reduck.data.protection.env;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

/**
 * @author Gin
 * @since 2023/5/23 13:51
 */

public class BeanFinder implements BeanFactoryPostProcessor {
    private static ConfigurableListableBeanFactory beanFactory;

    private final ApplicationContext applicationContext;

    public BeanFinder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanFinder.beanFactory = beanFactory;
    }

    public static <T> Optional<T> findBean(Class<T> beanType) {
        try {
            return Optional.of(beanFactory.getBean(beanType));
        } catch (BeansException e) {
            return Optional.empty();
        }
    }
}
