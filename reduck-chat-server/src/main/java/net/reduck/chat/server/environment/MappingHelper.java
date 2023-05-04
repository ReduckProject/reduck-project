package net.reduck.chat.server.environment;

import net.reduck.chat.server.annotation.EndpointMapping;
import net.reduck.chat.server.endpoint.LoginEndpoint;
import net.reduck.chat.server.handler.EndpointMappingDescriptor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Reduck
 * @since 2023/1/10 09:17
 */
public class MappingHelper {

    public void resolve(Class<?> endpoint) {
        EndpointMapping mapping = AnnotationUtils.findAnnotation(endpoint, EndpointMapping.class);
        String pathPrefix = getCanonicalPath(mapping);

        Method[] methods = endpoint.getMethods();
        for (Method method : methods) {
            EndpointMapping methodMapping = AnnotationUtils.findAnnotation(method, EndpointMapping.class);
            if(methodMapping != null) {
                EndpointMappingDescriptor mappingDescriptor = new EndpointMappingDescriptor();
                String path = pathPrefix + getCanonicalPath(methodMapping);
                mappingDescriptor.setEndpoint(endpoint);
                mappingDescriptor.setEndpointMappingMethod(method);
//                mappingDescriptor.setEndpointArgumentDescriptors();
                Annotation[][] annotations = method.getParameterAnnotations();
            }
        }
    }

    public static String getCanonicalPath(EndpointMapping mapping) {
        if(mapping == null) {
            return "";
        }

        return getCanonicalPath(mapping.value());
    }

    public static String getCanonicalPath(String path) {
        if(StringUtils.isEmpty(path)) {
            return "";
        }

        if(!path.startsWith("/")) {
            path = "/" + path;
        }

        while (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }

        return path;
    }

    public static void main(String[] args) {
        Method[] methods = LoginEndpoint.class.getMethods();

        for(Method method : methods) {
            Annotation[][] annotations = method.getParameterAnnotations();
            System.out.println();
        }
    }
}
