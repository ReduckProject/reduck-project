package net.reduck.chat.server.environment;

import net.reduck.chat.server.handler.EndpointMappingDescriptor;
import net.reduck.chat.server.reslover.AnnotationResolver;

import java.lang.annotation.Annotation;
import java.util.Optional;

/**
 * @author Reduck
 * @since 2023/1/10 09:59
 */
public class EnvironmentBeanUtils {

    public static Optional<Object> findEndpointInstance(Class<?> endpointClass) {
        return Optional.ofNullable(getEnvironment().getEndpointInstanceMap().get(endpointClass));
    }

    public static Optional<EndpointMappingDescriptor> findEndpointMappingDescriptor(String path) {
        // get endpoint mapping canonical path
        String canonicalPath = MappingHelper.getCanonicalPath(path);
        return Optional.ofNullable(getEnvironment().getMappingDescriptorMap().get(canonicalPath));
    }

    public static AnnotationResolver[] findAnnotationResolvers(Class<Annotation> annotationType) {
        return new AnnotationResolver[0];
    }

    private static ApplicationEnvironment getEnvironment() {
        return ApplicationEnvironmentHolder.getEnvironment();
    }
}
