package net.reduck.chat.server.reslover;

import java.lang.annotation.Annotation;

/**
 * @author Reduck
 * @since 2023/1/10 09:39
 */
public interface AnnotationResolver {

    public <T> T resolve(String data, Class<T> targetType);

    Class<? extends Annotation> getSupportAnnotation();
}
