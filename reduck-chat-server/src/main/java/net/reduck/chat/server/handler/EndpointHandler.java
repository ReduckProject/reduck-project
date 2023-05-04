package net.reduck.chat.server.handler;

import lombok.RequiredArgsConstructor;
import net.reduck.chat.server.annotation.Endpoint;
import net.reduck.chat.server.environment.ApplicationEnvironmentHolder;
import net.reduck.chat.server.environment.EnvironmentBeanUtils;
import net.reduck.chat.server.environment.MappingHelper;
import net.reduck.chat.server.pojo.ChatRequest;
import net.reduck.chat.server.reslover.AnnotationResolver;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author Reduck
 * @since 2023/1/9 15:43
 */
@RequiredArgsConstructor
public class EndpointHandler {

    public Object handle(ChatRequest<String> request) throws InvocationTargetException, IllegalAccessException {

        EndpointMappingDescriptor descriptor = EnvironmentBeanUtils.findEndpointMappingDescriptor(request.getEndpoint())
                .orElseThrow(() -> new RuntimeException("Path of"  + request.getEndpoint() + " not exist"));

        if (descriptor == null) {
            throw new IllegalArgumentException("Endpoint not exist");
        }

        Object[] arguments = new Object[descriptor.getEndpointArgumentDescriptors().length];
        int i = 0;
        for (EndpointArgumentDescriptor endpointArgumentDescriptor : descriptor.getEndpointArgumentDescriptors()) {
            Annotation[] paramAnnotations = endpointArgumentDescriptor.getAnnotationType();

            for(Annotation annotationType : paramAnnotations) {
                AnnotationResolver[] resolvers = EnvironmentBeanUtils.findAnnotationResolvers((Class<Annotation>) annotationType.getClass());
                // should have zero or one resolver
                for (AnnotationResolver resolver : resolvers) {
                    arguments[i] = resolver.resolve(request.getData(), endpointArgumentDescriptor.getParameterType());
                }
            }

            i++;
        }

        // find endpoint instance
        Object endpointInstance = EnvironmentBeanUtils.findEndpointInstance(descriptor.getEndpoint())
                .orElseThrow(() -> new RuntimeException("Instance of " + descriptor.getEndpoint().getName() + " not exist"));

        return descriptor.getEndpointMappingMethod().invoke(endpointInstance, (Object[]) arguments);
    }

    public static void main(String[] args) {
//        ServiceLoader<AnnotationHandler> serviceLoader = ServiceLoader.load(AnnotationHandler.class);
//        for(AnnotationHandler handler : serviceLoader) {
//            System.out.println(handler.getClass().getName());
//        }
//
//        System.out.println();

        Reflections reflections = new Reflections();
        Set<Class<? extends AnnotationHandler>> classes = reflections.getSubTypesOf(AnnotationHandler.class);
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(Endpoint.class);

        System.out.println(classes);
        System.out.println(classSet);
    }
}
