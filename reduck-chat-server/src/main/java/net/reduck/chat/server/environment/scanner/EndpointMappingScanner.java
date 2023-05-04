package net.reduck.chat.server.environment.scanner;

import net.reduck.chat.server.annotation.EndpointMapping;
import net.reduck.chat.server.environment.MappingHelper;
import net.reduck.chat.server.handler.EndpointArgumentDescriptor;
import net.reduck.chat.server.handler.EndpointMappingDescriptor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Reduck
 * @since 2023/1/10 10:36
 */
public class EndpointMappingScanner {

    private final Set<Class<?>> endpointClassSet;

    public EndpointMappingScanner(Set<Class<?>> endpointClassSe) {
        this.endpointClassSet = endpointClassSe;
    }

    public List<EndpointMappingDescriptor> scan() {
        List<EndpointMappingDescriptor> descriptors = new ArrayList<>();

        for(Class<?> endpointClass : endpointClassSet) {
            descriptors.addAll(resolve(endpointClass));
        }

        return descriptors;
    }

    private List<EndpointMappingDescriptor> resolve(Class<?> endpoint) {
        EndpointMapping mapping = AnnotationUtils.findAnnotation(endpoint, EndpointMapping.class);
        String pathPrefix = MappingHelper.getCanonicalPath(mapping);
        List<EndpointMappingDescriptor> descriptors = new ArrayList<>();

        Method[] methods = endpoint.getMethods();
        for (Method method : methods) {
            EndpointMapping methodMapping = AnnotationUtils.findAnnotation(method, EndpointMapping.class);
            if(methodMapping != null) {
                EndpointMappingDescriptor mappingDescriptor = new EndpointMappingDescriptor();
                String canonicalPath = pathPrefix + MappingHelper.getCanonicalPath(methodMapping);
                mappingDescriptor.setCanonicalPath(canonicalPath);
                mappingDescriptor.setEndpoint(endpoint);
                mappingDescriptor.setEndpointMappingMethod(method);
                mappingDescriptor.setEndpointArgumentDescriptors(resolveMethodParameters(method));

                descriptors.add(mappingDescriptor);
            }
        }
        return descriptors;
    }

    private EndpointArgumentDescriptor[] resolveMethodParameters(Method method) {
        EndpointArgumentDescriptor[] descriptors = new EndpointArgumentDescriptor[method.getParameterCount()];

        Parameter[] parameters = method.getParameters();
        int i = 0;
        for(Parameter parameter : parameters) {
            EndpointArgumentDescriptor descriptor = new EndpointArgumentDescriptor();
            descriptor.setParameterType(parameter.getType());
            descriptor.setAnnotationType(parameter.getAnnotations());
            descriptors[i] = descriptor;
            i++;
        }

        return descriptors;
    }
}
