package net.reduck.chat.server.reslover;

import net.reduck.chat.server.annotation.EndpointBody;
import net.reduck.validator.JsonUtils;

import java.lang.annotation.Annotation;

/**
 * @author Reduck
 * @since 2023/1/10 09:37
 */
public class EndpointBodyResolver implements AnnotationResolver {

    @Override
    public <T> T resolve(String data, Class<T> targetType) {
        return JsonUtils.json2Object(data, targetType);
    }

    @Override
    public Class<? extends Annotation> getSupportAnnotation() {
        return EndpointBody.class;
    }
}
