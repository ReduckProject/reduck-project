package net.reduck.jpa.test.hbm;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * @author Reduck
 * @since 2022/9/21 09:57
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {

        // A SessionFactory is set up once for an application!
//        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
//                .configure() // configures settings from hibernate.cfg.xml
//                .build();
//        try {
//            return new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        } catch (Exception e) {
//            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
//            // so destroy it manually.
//            StandardServiceRegistryBuilder.destroy(registry);
//        }

        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure()
//                    .addProperties(getConfig())
                    .buildSessionFactory();
        } catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
//        throw new RuntimeException();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

//    public static Properties getConfig() {
//        Properties properties = new Properties();
//        properties.setProperty("connection.driver_class", Driver.class.getName());
//        properties.setProperty("connection.url", "jdbc:mysql://localhost:3306/jpa_test?allowPublicKeyRetrieval=true&createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8&characterSetResults=utf8&useSSL=false&serverTimezone=GMT%2B8");
//        properties.setProperty("connection.username", "root");
//        properties.setProperty("connection.password", "111111");
//        properties.setProperty("connection.pool_size", "2");
//        properties.setProperty("dialect", MySQL57Dialect.class.getName());
//        properties.setProperty("current_session_context_class", "thread");
//        properties.setProperty("cache.provider_class", "org.hibernate.cache.NoCacheProvider");
//        properties.setProperty("show_sql", "true");
//        properties.setProperty("hbm2ddl.auto", "none");
//
//
//        return properties;
//    }
}
