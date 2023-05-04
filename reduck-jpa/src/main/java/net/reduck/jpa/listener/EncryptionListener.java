//package net.reduck.jpa.listener;
//
//import org.hibernate.event.service.spi.EventListenerRegistry;
//import org.hibernate.event.spi.*;
//import org.hibernate.internal.SessionFactoryImpl;
//import org.hibernate.jpa.HibernateEntityManagerFactory;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//import org.springframework.core.annotation.AnnotationUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.util.ReflectionUtils;
//
//import javax.persistence.EntityManagerFactory;
//import java.lang.reflect.Field;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
///**
// * @author Reduck
// * @since 2022/11/1 11:26
// */
//@Component
//public class EncryptionListener implements PreInsertEventListener, PreUpdateEventListener, PostLoadEventListener {
//    @Autowired
//    private FieldEncrypter fieldEncrypter;
//    @Autowired
//    private FieldDecrypter fieldDecrypter;
//    @Override
//    public void onPostLoad(PostLoadEvent event) {
//        fieldDecrypter.decrypt(event.getEntity());
//    }
//    @Override
//    public boolean onPreInsert(PreInsertEvent event) {
//        Object[] state = event.getState();
//        String[] propertyNames = event.getPersister().getPropertyNames();
//        Object entity = event.getEntity();
//        fieldEncrypter.encrypt(state, propertyNames, entity);
//        return false;
//    }
//    @Override
//    public boolean onPreUpdate(PreUpdateEvent event) {
//        Object[] state = event.getState();
//        String[] propertyNames = event.getPersister().getPropertyNames();
//        Object entity = event.getEntity();
//        fieldEncrypter.encrypt(state, propertyNames, entity);
//        return false;
//    }
//
//    public class Encrypter {
//        public String encrypt(String value) {
//            return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
//        }
//    }
//
//    public class Decrypter {
//        public String decrypt(String value) {
//            return new String(Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);
//        }
//    }
//
//    public class FieldEncrypter {
//        @Autowired
//        private Encrypter encrypter;
//        public void encrypt(Object[] state, String[] propertyNames, Object entity) {
//            ReflectionUtils.doWithFields(entity.getClass(), field -> encryptField(field, state, propertyNames), EncryptionUtils::isFieldEncrypted);
//        }
//        private void encryptField(Field field, Object[] state, String[] propertyNames) {
//            int propertyIndex = EncryptionUtils.getPropertyIndex(field.getName(), propertyNames);
//            Object currentValue = state[propertyIndex];
//            if (!(currentValue instanceof String)) {
//                throw new IllegalStateException("Encrypted annotation was used on a non-String field");
//            }
//            state[propertyIndex] = encrypter.encrypt(currentValue.toString());
//        }
//    }
//
//    public abstract class EncryptionUtils {
//        public static boolean isFieldEncrypted(Field field) {
//            return AnnotationUtils.findAnnotation(field, Encrypted.class) != null;
//        }
//        public static int getPropertyIndex(String name, String[] properties) {
//            for (int i = 0; i < properties.length; i++) {
//                if (name.equals(properties[i])) {
//                    return i;
//                }
//            }
//            throw new IllegalArgumentException("No property was found for name " + name);
//        }
//    }
//
//    public class FieldDecrypter {
//        @Autowired
//        private Decrypter decrypter;
//        public void decrypt(Object entity) {
//            ReflectionUtils.doWithFields(entity.getClass(), field -> decryptField(field, entity), EncryptionUtils::isFieldEncrypted);
//        }
//        private void decryptField(Field field, Object entity) {
//            field.setAccessible(true);
//            Object value = ReflectionUtils.getField(field, entity);
//            if (!(value instanceof String)) {
//                throw new IllegalStateException("Encrypted annotation was used on a non-String field");
//            }
//            ReflectionUtils.setField(field, entity, decrypter.decrypt(value.toString()));
//        }
//    }
//
//    public class EncryptionBeanPostProcessor implements BeanPostProcessor {
//        private final Logger logger = LoggerFactory.getLogger(EncryptionBeanPostProcessor.class);
//        @Autowired
//        private EncryptionListener encryptionListener;
//        @Override
//        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//            return bean;
//        }
//        @Override
//        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//            if (bean instanceof EntityManagerFactory) {
//                HibernateEntityManagerFactory hibernateEntityManagerFactory = (HibernateEntityManagerFactory) bean;
//                SessionFactoryImpl sessionFactoryImpl = (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();
//                EventListenerRegistry registry = sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);
//                registry.appendListeners(EventType.POST_LOAD, encryptionListener);
//                registry.appendListeners(EventType.PRE_INSERT, encryptionListener);
//                registry.appendListeners(EventType.PRE_UPDATE, encryptionListener);
//                logger.info("Encryption has been successfully set up");
//            }
//            return bean;
//        }
//    }
//}